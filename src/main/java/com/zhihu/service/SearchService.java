package com.zhihu.service;

import com.zhihu.model.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by victorz on 2017/7/23.
 * 搜索服务--调用solr
 */
@Service
public class SearchService {
    //官方格式
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/wenda";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    //直接调用solr服务
    //使用方法写在官网上


    //1.查询--关键词，偏移，高亮位置
    public List<Question> searchQuestion(String keyword, int offset, int count, String hlPre, String hlPos) throws Exception{

        List<Question> resultList = new ArrayList<>();
        SolrQuery query = new SolrQuery(keyword);//官方格式
        //设置配置格式
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        //高亮字符串限制，如果不设置可能会字符串显示不完整
        query.setHighlightFragsize(100000);
        query.set("hl.fl", "question_title" + "," + "question_content");

        //查询
        QueryResponse response = client.query(query);
        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Question q = new Question();
            q.setId(Integer.parseInt(entry.getKey()));
            if (entry.getValue().containsKey("question_content")) {
                List<String> contentList = entry.getValue().get("question_content");
                if (contentList.size() > 0) {
                    q.setContent(contentList.get(0));
                }
            }
            if (entry.getValue().containsKey("question_title")) {
                List<String> titleList = entry.getValue().get("question_title");
                if (titleList.size() > 0) {
                    q.setTitle(titleList.get(0));
                }
            }
            resultList.add(q);
        }
        return resultList;
    }

    //2.建立索引
    //问题索引
    public boolean indexQuestion(int qid, String title, String content) throws Exception {
        SolrInputDocument doc =  new SolrInputDocument();
        doc.setField("question_id", qid);
        doc.setField("question_title", title);
        doc.setField("question_content", content);
        UpdateResponse response = client.add(doc, 1000);
        return response != null && response.getStatus() == 0;
    }

//    //评论索引
//    public boolean indexComment(int cid, String content) throws Exception {
//        SolrInputDocument doc =  new SolrInputDocument();
//        doc.setField("comment_id", cid);
//        doc.setField("comment_content", content);
//        UpdateResponse response = client.add(doc, 1000);
//        return response != null && response.getStatus() == 0;
//    }

//   //用户索引
//    public boolean indexUser(int uid, String name) throws Exception {
//        SolrInputDocument doc =  new SolrInputDocument();
//        doc.setField("user_id", uid);
//        doc.setField("user_name", name);
//        UpdateResponse response = client.add(doc, 1000);
//        return response != null && response.getStatus() == 0;
//    }
}
