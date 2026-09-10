package com.beentteum.seatfilter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CafeController {

    private final List<Map<String, Object>> mockCafes = List.of(
        Map.of("id", 1, "name", "카페 A", "hasOutlet", true, "isQuiet", true, "hasWifi", true),
        Map.of("id", 2, "name", "카페 B", "hasOutlet", true, "isQuiet", false, "hasWifi", true),
        Map.of("id", 3, "name", "카페 C", "hasOutlet", false, "isQuiet", true, "hasWifi", true),
        Map.of("id", 4, "name", "카페 D", "hasOutlet", false, "isQuiet", false, "hasWifi", false)
    );

    @GetMapping("/api/cafes/filter")
    public List<Map<String, Object>> filterCafes(
            @RequestParam(required = false) Boolean needOutlet,
            @RequestParam(required = false) Boolean quietOnly,
            @RequestParam(required = false) Boolean needWifi) {

        return mockCafes.stream()
                .filter(cafe -> needOutlet == null || needOutlet.equals(cafe.get("hasOutlet")))
                .filter(cafe -> quietOnly == null || quietOnly.equals(cafe.get("isQuiet")))
                .filter(cafe -> needWifi == null || needWifi.equals(cafe.get("hasWifi")))
                .map(cafe -> {
                    Map<String, Object> result = new HashMap<>(cafe);
                    List<String> tags = new ArrayList<>();
                    if (Boolean.TRUE.equals(needOutlet) && Boolean.TRUE.equals(cafe.get("hasOutlet"))) {
                        tags.add("콘센트 좌석 확보");
                    }
                    if (Boolean.TRUE.equals(quietOnly) && Boolean.TRUE.equals(cafe.get("isQuiet"))) {
                        tags.add("조용한 집중 환경");
                    }
                    if (Boolean.TRUE.equals(needWifi) && Boolean.TRUE.equals(cafe.get("hasWifi"))) {
                        tags.add("무선 인터넷 제공");
                    }
                    result.put("matchedTags", tags);
                    return result;
                })
                .collect(Collectors.toList());
    }
}
