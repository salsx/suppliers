package services.search.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


import models.MultimediaContent;
import models.MultimediaType;
import models.Registration;
import models.response.RepositoryResponseMapping;
import models.response.WikipediaRepositoryResponseMapping;
import play.Logger;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;
import services.nuxeo.NuxeoService;
/**
 * Created by Giuseppe on 29/08/2017.
 */
public class WikipediaSearchRepository  implements SearchRepository {

    private WSClient ws;
    private Registration reg;

    @Inject
    public WikipediaSearchRepository(WSClient ws, Registration registration){
        this.ws=ws;
        this.reg=registration;
    }

    @Override
    public CompletionStage<JsonNode> executeQuery( List<String> keyWords){
        String query="";
        for(String s : keyWords){
            query+=s;
            query+="+";
        }
        //Logger.info("Wikipedia search: "+query);
        CompletionStage<JsonNode> jsonPromise;
        jsonPromise = ws.url(reg.getRepository().getURI()).
                setQueryParameter("action", "opensearch").
                setQueryParameter("search", query).
                setQueryParameter("format", "json").
                get().
                thenApply(WSResponse::asJson);
        return jsonPromise;
    }


	@Override
    public RepositoryResponseMapping transform(JsonNode clientResponse ) {
        //Logger.info("Wikipedia Response1: "+clientResponse.get(1));
        //Logger.info("Wikipedia Response2: "+clientResponse.get(2));
        //Logger.info("Wikipedia ResponseALL: "+clientResponse);
        
        WikipediaRepositoryResponseMapping respMapping=new WikipediaRepositoryResponseMapping();
        List<MultimediaContent> stages=new ArrayList<>();
        //List<JsonNode> items=clientResponse.findValues("items");
        //NuxeoService ns = new NuxeoService();
 
        if(clientResponse.get(1)!=null) {
            ArrayNode titleArray = (ArrayNode) clientResponse.get(1);
            ArrayNode descArray  = (ArrayNode) clientResponse.get(2);
            ArrayNode linkArray  = (ArrayNode) clientResponse.get(3);

            

            respMapping.setnOfResults(clientResponse.get(1).size());

            List<ArrayNode> itemsList = new ArrayList<ArrayNode>();
            ArrayNode item;
            for(int i = 0; i < titleArray.size(); i++ ) {
            	//titleArray.get(i)+","+descArray.get(i)+","+linkArray.get(i)
            	 JsonNode title = titleArray.get(i);
            	 JsonNode desc = descArray.get(i);
            	 JsonNode link = linkArray.get(i);
            	
//            	 Logger.info("title:"+titleArray.get(i).asText());
//            	 Logger.info("desc:"+descArray.get(i).asText());
//            	 Logger.info("link:"+linkArray.get(i).asText());
            	 
            	 item = new ArrayNode(null);
            	 item.add(title);
            	 item.add(desc);
            	 item.add(link);
            	 
            	itemsList.add(i, item);
            }
            
            //Logger.info("ITEM:"+itemsList);
            

            //Logger.info("Nuxeo Service" +"->"+ns.create());
            

            

        /*final List<MultimediaContent> multimediaContents = new ArrayList<MultimediaContent>();
        if(items.isArray()) {
            items.forEach(( JsonNode i ) -> multimediaContents.add(getMultimediaContentFromItem(i)));
        }

        return multimediaContents;*/
            Function<JsonNode, MultimediaContent> convertToMultimediaContent =
                    jsonNode -> getMultimediaContentFromItem(jsonNode);
                    
            if (!itemsList.isEmpty()) {
                stages = itemsList
                        .stream()
                        .map(convertToMultimediaContent)
                        .collect(Collectors.toList());
            }
        }
        respMapping.setMultimediaContents(stages);
        return respMapping;
    }


	private MultimediaContent getMultimediaContentFromItem( JsonNode i ) {
    	String name = "";
    	String description = "";
    	String link = "";
		
		if (i.get(0) != null){
    		name = i.get(0).asText();
    	}
    	if (i.get(1) != null){
    		description = i.get(1).asText();
    	}
    	if (i.get(2) != null){
    		link = i.get(2).asText();
    	}
        //CompletionStage<MultimediaContent> multimediaContent=CompletableFuture.supplyAsync( () -> {
        MultimediaContent m = new MultimediaContent();
        //m.setType(i.path("id").get("kind").asText());
        m.setType(MultimediaType.text);
        m.setURI(link);
        //m.setDownloadURI(reg.getRepository().getUrlPrefix() + i.path("id").get("videoId").asText());
        m.setName(name);
        m.setDescription(description);
        m.setSource(reg.getRepository());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        try {
//            m.setDate(sdf.parse(i.get("snippet").get("publishedAt").asText()));
//            //Logger.debug("*********DATE:"+sdf.parse(i.get("snippet").get("publishedAt").asText()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        // TODO: Modify to find SearchRepository from DB
        //m.setSource(reg.getRepository());
        //Logger.debug("Debug multimedia enum:"+m.toString());
        return m;
    }
}
