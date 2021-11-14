package DAO;

import Model.Token;
import Exception.*;

import java.io.IOException;

public interface TokenDAO {

	Token getToken() throws IOException, BadRequestException;

}
