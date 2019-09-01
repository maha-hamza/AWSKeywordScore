package com.aws.keyword.score;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AwsSearchScore {

    private String keyword;
    private int score;

}

