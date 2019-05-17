package com.company.project.sysmgr.demo.domain;

import com.company.project.common.domain.BaseEntity;
import lombok.Data;

@Data
public class CommentText implements BaseEntity {
    private String text;
}
