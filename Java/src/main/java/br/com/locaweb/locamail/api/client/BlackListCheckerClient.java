package br.com.locaweb.locamail.api.client;

import br.com.locaweb.locamail.api.config.FeignClientConfiguration;
import br.com.locaweb.locamail.api.dto.BlackListResponse;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "blackList",
        url = "https://api.blacklistchecker.com",
        configuration = FeignClientConfiguration.class
)
public interface BlackListCheckerClient {
    @GetMapping(value = "/check/{domain}", consumes = MediaType.APPLICATION_JSON_VALUE)
    BlackListResponse blackListVerify(
            @PathVariable(value = "domain") String email);
}