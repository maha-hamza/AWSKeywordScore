package com.aws.keyword.score;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AwsSearchScoreResponse {

    private Boolean failed;
    private Object data;
}
