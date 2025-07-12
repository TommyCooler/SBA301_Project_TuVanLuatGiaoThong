package sba.project.tuvanluatgiaothong.repository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sba.project.tuvanluatgiaothong.pojo.Token;
import sba.project.tuvanluatgiaothong.pojo.User;

import java.util.List;

@Service
@Transactional
public class TokenTransaction {

    private final TokenRepository tokenRepository;

    public TokenTransaction(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveToken(User user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        token.setUser(user);

        tokenRepository.save(token);
    }

    @Transactional
    public void revokeAllOldUserToken(User user) {
        List<Token> tokens = tokenRepository.findAllValidTokenByUsername(user.getId());
        tokenRepository.deleteAll(tokens);
    }
}
