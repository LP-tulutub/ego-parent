package com.ego;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


/**
 * 增加删除修改都需要提交事务
 * @author Administrator
 *
 */
public class SolrCloudDemo {
	
	/**
	 * 新增5
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void testInsert() throws SolrServerException, IOException{
		CloudSolrClient client = new CloudSolrClient("192.168.1.12:2181,192.168.1.12:2182,192.168.1.12:2183");
		client.setDefaultCollection("collection1");
		SolrInputDocument doc = new SolrInputDocument();
		//下面只能一次一次来
//		doc.addField("id", "001");
//		doc.addField("LPTab1", "java和python");
//		doc.addField("id", "002");
//		doc.addField("LPTab1", "python3.6");
//		doc.addField("LPTab2", "python3.7");
		doc.addField("id", "003");
		doc.addField("LPTab1", "java1.8");
		
		client.add(doc);
		client.commit();
	}
	@Test
	public void testDelete() throws SolrServerException, IOException{
		CloudSolrClient client = new CloudSolrClient("192.168.1.12:2181,192.168.1.12:2182,192.168.1.12:2183");
		client.setDefaultCollection("collection1");
		client.deleteById("001");
		client.commit();
	}
	@Test
	public void testQuery() throws SolrServerException, IOException{
		CloudSolrClient client = new CloudSolrClient("192.168.1.12:2181,192.168.1.12:2182,192.168.1.12:2183");
		client.setDefaultCollection("collection1");

		//可视化界面左侧条件
		SolrQuery params = new SolrQuery();
		//设置q
		params.setQuery("LPTabAll:python");
		//设置分页
		//从第几条开始查询,从0开始
		params.setStart(0);
		//查询几个
		params.setRows(10);


		//启动高亮
		params.setHighlight(true);
		//设置高亮列
		params.addHighlightField("LPTab1");
		//设置前缀
		params.setHighlightSimplePre("<span style='color:red;'>");
		//设置后缀
		params.setHighlightSimplePost("</span>");

		//相当于点击查询按钮, 本质,向solr web服务器发送请求,并接收响应. query对象里面包含了返回json数据
		QueryResponse response = client.query(params);

		Map<String, Map<String, List<String>>> hh = response.getHighlighting();


		//取出docs{}
		SolrDocumentList solrList = response.getResults();

		for (SolrDocument doc : solrList) {
			System.out.println(doc.getFieldValue("id"));
			System.out.println("未高亮:"+doc.getFieldValue("LPTab1"));
			Map<String, List<String>> map = hh.get(doc.getFieldValue("id"));
			//list可能为null
			List<String> list = map.get("LPTab1");
			System.out.println(list);
			if(list!=null&&list.size()>0){
				System.out.println("高亮:"+list.get(0));
			}else{
				System.out.println("没有高亮内容");
			}
			System.out.println(doc.getFieldValue("LPTab2"));
			System.out.println("--------------------------------------");
		}
		
	}
	
}
