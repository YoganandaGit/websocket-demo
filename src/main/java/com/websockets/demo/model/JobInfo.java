package com.websockets.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JobInfo {

    @JsonProperty
    private String phase;

    @JsonProperty
    private String process;

    @JsonProperty
    private String state;

    @JsonProperty
    private String systemId;

    @JsonProperty
    private String jobId;

}
