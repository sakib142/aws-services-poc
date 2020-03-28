import java.math.BigInteger;

import org.assertj.core.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trax.ems.marketdata.model.MarketDepth;
import com.trax.ems.marketdata.model.RequestDTO;

public class Test {

    public static void main1(String[] args) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        RequestDTO dto = new RequestDTO();
        dto.setMethod("GET");
        MarketDepth md = new MarketDepth();
        md.setInstId(BigInteger.ONE);
        md.setId(BigInteger.ONE);
        md.setSourceVenue("TEST");
        dto.setData(md);
        System.out.println(om.writeValueAsString(dto));
    
    }
    
    public static void main(String[] args) {
        System.out.println(Arrays.asList(null));
    }

}
