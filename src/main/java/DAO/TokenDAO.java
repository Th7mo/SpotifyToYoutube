package DAO;

import Model.Token;

import java.io.IOException;

public interface TokenDAO {

	Token getToken() throws IOException;
}
