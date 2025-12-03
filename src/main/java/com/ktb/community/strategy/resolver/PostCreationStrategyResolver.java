package com.ktb.community.strategy.resolver;

import com.ktb.community.domain.enums.PostType;
import com.ktb.community.strategy.post.PostCreationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PostCreationStrategyResolver {

    private final Map<PostType, PostCreationStrategy> postCreationStrategyMap;

    public PostCreationStrategyResolver(List<PostCreationStrategy> postCreationStrategies) {
        this.postCreationStrategyMap = postCreationStrategies.stream()
                .collect(Collectors.toMap(
                        PostCreationStrategy::getPostType,
                        strategy -> strategy
                ));
    }

    public PostCreationStrategy getStrategy(PostType postType) {
        return postCreationStrategyMap.get(postType);
    }
}
