package dev.cheng.spring.task;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AntBangRestartTask {

    private final RestTemplate restTemplate;

    private int count = 0;

    @Scheduled(fixedRate = 10 * 1000)
    public void executeTask() {
        log.info("Start to login");
        String url1 =
                "http://192.168.249.1/protocol.csp?fname=system&opt=login&function=set&usrid"
                        + "=55619361955d59df037eeaa7cb95ec6d";
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Accept", "application/json, text/javascript, */*; q=0.01");
        headers1.add("Referer", "http://192.168.249.1/");
        headers1.add("X-Requested-With", "XMLHttpRequest");

        HttpEntity<String> entity1 = new HttpEntity<>(headers1);
        ResponseEntity<String> response1 = restTemplate.postForEntity(url1, entity1, String.class);

        JSONObject jsonResponse1 = new JSONObject(response1.getBody());
//        log.info("Login result: {}", jsonResponse1);
        boolean loginSuccess = jsonResponse1.getInt("error") == 0;
        log.info("Login {}", loginSuccess ? "success" : "fail");
        if (!loginSuccess) {
            return;
        }

        String token = jsonResponse1.getString("token");

        String url2 = "http://192.168.249.1/protocol.csp?token=" + token
                + "&fname=system&opt=setting&action=reboot&function=set";
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Accept", "application/json, text/javascript, */*; q=0.01");
        headers2.add("Referer", "http://192.168.249.1/user/index.html?tt=1724250446573");
        headers2.add("X-Requested-With", "XMLHttpRequest");

        HttpEntity<String> entity2 = new HttpEntity<>(headers2);
        ResponseEntity<String> response2 = restTemplate.postForEntity(url2, entity2, String.class);

        JSONObject jsonResponse2 = new JSONObject(response2.getBody());
        log.info("Restart result: {}", jsonResponse2);
        boolean restartSuccess = jsonResponse2.getInt("error") == 0;
        if (restartSuccess) {
            count++;
        }
        log.info("Restart {}, count: {}", restartSuccess ? "success" : "fail", count);

    }
}
