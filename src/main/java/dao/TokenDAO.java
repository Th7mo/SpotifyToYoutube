package dao;

import model.Token;
import exception.*;

import java.io.IOException;

public interface TokenDAO {

    Token getToken() throws IOException, BadRequestException;
}
