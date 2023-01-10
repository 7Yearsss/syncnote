package com.xxb.notesync.utls;

import lombok.Data;

@Data
public class PagingResultParam {

    public long totalCount = 0L;

    public Long pageNo;

    public Long totalPage = 0L;

    public Long limit;
}
