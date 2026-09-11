package com.beentteum.seatfilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SeatFilterTest {

    private final CafeController controller = new CafeController();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("TC-01 [정상]: 조건 누락 시 전체 데이터(4건) 조회")
    void tc01_findAllWhenNoFilter() {
        List<Map<String, Object>> result = controller.filterCafes(null, null, null);
        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("TC-02 [정상]: 단일 조건 검증 (콘센트 구비 매장 2건: 카페 A, 카페 B)")
    void tc02_filterByOutlet() {
        List<Map<String, Object>> result = controller.filterCafes(true, null, null);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("TC-03 [정상]: 복합 조건 검증 (콘센트 O + 조용함 O -> 카페 A 1건)")
    void tc03_filterByOutletAndQuiet() {
        List<Map<String, Object>> result = controller.filterCafes(true, true, null);
        assertEquals(1, result.size());
        assertEquals("카페 A", result.get(0).get("name"));
    }

    @Test
    @DisplayName("TC-04 [정상]: 불일치 조건 시 0건 빈 배열 반환 검증")
    void tc04_filterEmptyResult() {
        List<Map<String, Object>> result = controller.filterCafes(true, true, false);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("TC-05 [예외]: 잘못된 파라미터 타입 유입 시 400 Bad Request 검증")
    void tc05_invalidParameterType_throws400() throws Exception {
        mockMvc.perform(get("/api/cafes/filter")
                        .param("needOutlet", "invalid"))
                .andExpect(status().isBadRequest());
    }
}
