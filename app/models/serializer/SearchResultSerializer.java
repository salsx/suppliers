package models.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import models.SearchResult;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Pasquale on 12/07/2017.
 */
public class SearchResultSerializer extends StdSerializer<SearchResult> {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public SearchResultSerializer() {
        this(null);
    }

    public SearchResultSerializer(Class<SearchResult> t) {
        super(t);
    }

    @Override
    public void serialize(
            SearchResult value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        if(value.getId()!=null)  jgen.writeStringField("id", value.getId().toHexString());
        jgen.writeObjectField("freeText", value.getKeyWords());
        if(value.getMultimediaContents()!=null)   jgen.writeObjectField("multimediaContents", value.getMultimediaContents());
        jgen.writeObjectField("date", FORMATTER.format(value.getDate()));
        /*jgen.writeObjectField("inDate", FORMATTER.format(value.getInDate()));
        jgen.writeObjectField("endDate", FORMATTER.format(value.getEndDate()));*/
        jgen.writeNumberField("nOfResults", value.getnOfResults());
        //jgen.writeObjectField("multimediaContents", value.getMultimediaContents());
        jgen.writeEndObject();
    }

}
