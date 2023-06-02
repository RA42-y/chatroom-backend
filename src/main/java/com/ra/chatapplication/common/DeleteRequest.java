package com.ra.chatapplication.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = -3944902619697782616L;

    private long id;
}
