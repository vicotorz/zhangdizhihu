package com.zhihu.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/6/4.
 */
@Service
public class SensetiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensetiveService.class);
    private static final String DEFAULT_REPLACEMENT = "***";

    //Spring通过InitializingBean完成一个bean初始化后，对这个bean的回调工作
    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();

        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    //树节点
    private class TrieNode {
        //词，词尾标志
        private boolean end = false;//词尾
        private Map<Character, TrieNode> subNodes = new HashMap<>();//词

        //向指定位置添加节点树
        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        //获取下个节点
        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    //根节点
    private TrieNode rootNode = new TrieNode();

    //判断是否是一个符号
    // 0x2E80-0x9FFF 东亚文字范围
    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    //添加敏感字符
    public void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        //循环每个字节
        for (int i = 0; i < lineTxt.length(); ++i) {
            Character c = lineTxt.charAt(i);
            //过滤空格
            TrieNode node = tempNode.getSubNode(c);

            //没初始化
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);//没有初始化，将字符放进去
            }

            tempNode = node;

            //将敏感词添加成功--设置end标志
            if (i == lineTxt.length() - 1) {
                //关键词结束，设置结束标志
                tempNode.setKeywordEnd(true);
            }
        }
    }

    //过滤敏感词
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = DEFAULT_REPLACEMENT;
        StringBuffer result = new StringBuffer();

        TrieNode tempNode = rootNode;
        int base = 0;//指针1
        int forward = 0;//指针2

        //这部分过滤词中的非中文部分
        while (forward < text.length()) {
            char c = text.charAt(forward);
            if (isSymbol(c)) {
                //先把第一个字符加上
                if (tempNode == rootNode) {
                    result.append(c);
                    base++;
                }
                ++forward;
                continue;
            }
            //找当前词在trie树中节点
            tempNode = tempNode.getSubNode(c);

            //三种情况
            //1、没有找到节点    forward=base+1;base=forward;
            //2、找到节点        forward=forward+1;base=farward;
            //3、不确定现在是不是 ++forward;
            if (tempNode == null) {//没找到
                //以begin开始的字符串不存在敏感词
                result.append(text.charAt(base));
                //跳到下一个字符开始测试
                //System.out.println(result);
                forward = base + 1;
                base = forward;
                tempNode = rootNode;//回到树初始节点
            } else if (tempNode.isKeywordEnd()) {//找到
                //找到敏感词
                result.append(replacement);//替换
                //System.out.println(result);
                forward = forward + 1;
                base = forward;
                tempNode = rootNode;
            } else {//不确定
                //一直向前探索
                ++forward;
            }
        }
        result.append(text.substring(base));
        return result.toString();
    }

//    public static void main(String[] argv) {
//        SensetiveService s = new SensetiveService();
//        s.addWord("色情");
//        s.addWord("好色");
//        System.out.print(s.filter("你好色情"));
//        System.out.print(s.filter("我好爱情"));
//    }
}
