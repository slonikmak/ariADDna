/*
 * ariADDna API
 * #### This document contains the API description for ariADDna project. Using this API one can manage all available cloud services (DropBox, GDrive, Yandex.Disk etc.) from single point. 
 *
 * OpenAPI spec version: 1.0
 * Contact: ariaddna.support@stnetix.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Statistic;
import java.util.ArrayList;
import java.util.List;

/**
 * Contain set of statistic information about API object.
 */
@ApiModel(description = "Contain set of statistic information about API object.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-02-13T15:04:21.309Z")
public class StatisticSet   {
  @JsonProperty("statisticSet")
  private List<Statistic> statisticSet = new ArrayList<Statistic>();

  public StatisticSet statisticSet(List<Statistic> statisticSet) {
    this.statisticSet = statisticSet;
    return this;
  }

  public StatisticSet addStatisticSetItem(Statistic statisticSetItem) {
    this.statisticSet.add(statisticSetItem);
    return this;
  }

   /**
   * Get statisticSet
   * @return statisticSet
  **/
  @JsonProperty("statisticSet")
  @ApiModelProperty(required = true, value = "")
  public List<Statistic> getStatisticSet() {
    return statisticSet;
  }

  public void setStatisticSet(List<Statistic> statisticSet) {
    this.statisticSet = statisticSet;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatisticSet statisticSet = (StatisticSet) o;
    return Objects.equals(this.statisticSet, statisticSet.statisticSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statisticSet);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatisticSet {\n");
    
    sb.append("    statisticSet: ").append(toIndentedString(statisticSet)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

