package cn.smdq.ch.dream.auth.configurer;

import cn.smdq.ch.common.core.consts.CommonConsts;
import cn.smdq.ch.common.core.consts.SecurityConsts;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author shuimodanqing
 * create at:  2019/6/24  11:27 PM
 * @description
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class DreamAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final RedisConnectionFactory redisConnectionFactory;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService detailsService = new JdbcClientDetailsService(dataSource);
        detailsService.setSelectClientDetailsSql(SecurityConsts.DEFAULT_SELECT_STATEMENT);
        detailsService.setFindClientDetailsSql(SecurityConsts.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(detailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.reuseRefreshTokens(true)
                .tokenEnhancer(tokenEnhancer())
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CommonConsts.TOKEN_PREFIX);
        return tokenStore;
    }

    public TokenEnhancer tokenEnhancer() {
        return (token, authentication) -> {
            Map<String, Object> additional = token.getAdditionalInformation();
            additional.put("license", "made by dream");
            return token;
        };
    }
}
