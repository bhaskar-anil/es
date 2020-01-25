package in.taskoo.search.es.common.dto;

import lombok.Data;

@Data
public class GeoLocation {
  private Double longitude;
  private Double latitude;
  private String formattedAddress;
}
