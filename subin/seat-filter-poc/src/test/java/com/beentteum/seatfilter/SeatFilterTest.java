package com.beentteum.seatfilter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatFilterTest {

    private final CafeController controller = new CafeController();

    @Test
    @DisplayName("TC-01: 조건 누락 시 전체 데이터(4건) 조회")
    void tc01_findAllWhenNoFilter() {
        List<Map<String, Object>> result = controller.filterCafes(null, null, null);
        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("TC-02: 단일 조건 검증 (콘센트 구비 매장 2건: 카페 A, 카페 B)")
    void tc02_filterByOutlet() {
        List<Map<String, Object>> result = controller.filterCafes(true, null, null);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("TC-03: 복합 조건 검증 (콘센트 O + 조용함 O -> 카페 A 1건)")
    void tc03_filterByOutletAndQuiet() {
        List<Map<String, Object>> result = controller.filterCafes(true, true, null);
        assertEquals(1, result.size());
        assertEquals("카페 A", result.get(0).get("name"));
    }

    @Test
    @DisplayName("TC-04: 불일치 공집합 검증 (조건에 맞는 대상 없음 -> 0건 빈 배열)")
    void tc04_filterEmptyResult() {
        List<Map<String, Object>> result = controller.filterCafes(true, true, false);
        assertEquals(0, result.size());
    }
}
