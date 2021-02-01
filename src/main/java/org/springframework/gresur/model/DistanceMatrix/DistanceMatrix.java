
package org.springframework.gresur.model.DistanceMatrix;


import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "distances",
    "stops"
})
public class DistanceMatrix {

  @JsonProperty("distances")
  private List<Integer> distances;
  
  @JsonProperty("stops")
  private List<Map<String, Object>> stops;


}
