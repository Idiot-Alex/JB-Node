package com.hotstrip.jbnode.domain.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NodeModel {
    private String version;
    private String date;
    private String[] files;
    private String npm;
    private String lts;
}
