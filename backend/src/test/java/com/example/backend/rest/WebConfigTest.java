package com.example.backend.rest;

import com.example.backend.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.lang.reflect.Method;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WebConfigTest {


    @Test
    void testAddCorsMappings() throws Exception {
        // Create a real CorsRegistry instance
        CorsRegistry corsRegistry = new CorsRegistry();

        // Instantiate WebConfig and call the method under test
        WebConfig webConfig = new WebConfig();
        webConfig.addCorsMappings(corsRegistry);

        // Use reflection to access the protected getCorsConfigurations() method
        Method getCorsConfigurationsMethod = CorsRegistry.class.getDeclaredMethod("getCorsConfigurations");
        getCorsConfigurationsMethod.setAccessible(true);

        // Get the configurations
        @SuppressWarnings("unchecked")
        Map<String, ?> corsConfigurations = (Map<String, ?>) getCorsConfigurationsMethod.invoke(corsRegistry);

        // Verify the configurations
        assertThat(corsConfigurations).isNotEmpty();
        assertThat(corsConfigurations.containsKey("/**")).isTrue();
    }
}
