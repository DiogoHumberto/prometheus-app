package com.api.loja.mkt.scheduler;

import com.api.loja.mkt.model.UserModel;
import com.api.loja.mkt.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserScheduler {
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserTokenRepository userTokenRepository;
	
	public static final String EMAIL_USER_MASTER = "teste@gmail.com";

	public static final String SECRET_PASS_MASTER = "@Auth_#teste!";
	
	  @EventListener(ApplicationReadyEvent.class)
	    public void init(){
//	        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//	        Locale.setDefault(Constants.LOCALE_BRASIL);
		  
			if (!userTokenRepository.existsByEmail(EMAIL_USER_MASTER)) {

				userTokenRepository.save(UserModel.builder()
						.email(EMAIL_USER_MASTER)
						.password(passwordEncoder.encode(SECRET_PASS_MASTER)).build());

			}

	    }

}
