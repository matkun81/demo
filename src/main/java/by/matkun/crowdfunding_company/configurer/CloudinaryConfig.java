package by.matkun.crowdfunding_company.configurer;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "matkun");
        config.put("api_key", "322761257467638");
        config.put("api_secret", "0Z0KmB0457_7s_bZO-w1WR27iYs");
        return new Cloudinary(config);
    }

}
