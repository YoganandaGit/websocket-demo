package com.websockets.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo {

    private String phase;

    private String process;

    private String state;

    private String systemId;

    private String jobId;

}
