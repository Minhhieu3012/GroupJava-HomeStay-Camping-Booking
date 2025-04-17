package ut.edu.database.configurations;
//Cấu hình để mã hóa mk sử dụng BCryptPasswordEncoder

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Bean, Configuration - Spring Core, dung de danh dau class/config nao do
// BCryptPasswordEncoder - class ma hoa mk bang thuat toan bcrypt
// PasswordEncoder - interface dai dien cho cac encoder (ma hoa password)
// Annotation ("@"): chu thich cho Spring Core

@Configuration // xu li cac Bean ben trong class cau hinh nay
public class PasswordEncoderConfig {
    @Bean // tao 1 Bean, tuc la 1 obj Spring co the inject vao noi khac
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
