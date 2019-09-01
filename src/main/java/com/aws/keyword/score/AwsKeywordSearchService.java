package com.aws.keyword.score;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import net.ricecode.similarity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
class AwsKeywordSearchService {

    AwsSearchScoreResponse searchKeyWordAndFindScore(String keyword) {

        HttpResponse<ArrayList> response = null;
        try {
            response = Unirest.get("http://completion.amazon.com/search/complete")
                    .queryString("search-alias", "aps")
                    .queryString("client", "amazon-search-ui")
                    .queryString("mkt", "1")
                    .queryString("q", keyword)
                    .asObject(ArrayList.class);
        } catch (UnirestException e) {
            return AwsSearchScoreResponse
                    .builder()
                    .failed(true)
                    .data(e.getCause())
                    .build();
        }
        int score = 0;
        if (response.isSuccess()) {
            ArrayList<String> candidates = (ArrayList<String>) response.getBody().get(1);
            if (candidates.size() > 0) {
                SimilarityStrategy strategy = new JaroWinklerStrategy();
                StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
                for (String candidate : candidates) {
                    score += service.score(keyword, candidate) * 10;
                }

            }
        }

        return AwsSearchScoreResponse
                .builder()
                .failed(false)
                .data(
                        AwsSearchScore
                                .builder()
                                .keyword(keyword)
                                .score(score)
                                .build()
                )
                .build();
    }
}
