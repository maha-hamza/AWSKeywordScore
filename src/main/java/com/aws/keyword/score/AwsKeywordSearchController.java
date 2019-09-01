package com.aws.keyword.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsKeywordSearchController {

    @Autowired
    private AwsKeywordSearchService service;

    @GetMapping("/estimate")
    public AwsSearchScoreResponse checkScoreForAwsKeyword(@RequestParam("keyword") String keyword) {
        return service.searchKeyWordAndFindScore(keyword);
    }


}
