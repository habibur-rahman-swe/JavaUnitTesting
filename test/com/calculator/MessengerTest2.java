package com.calculator;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mockito.Client;
import com.mockito.Email;
import com.mockito.MailServer;
import com.mockito.Messenger;
import com.mockito.Template;
import com.mockito.TemplateEngine;

@ExtendWith(MockitoExtension.class)
class MessengerTest2 {

	private static final String RANDOM_MESSAGE = "Message";
	private static final String RANDOM_EMAIL = "email@gmail.com";
	
	@Mock
	private TemplateEngine templateEngineMock;
	
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private MailServer mailServerMock;
	
	@InjectMocks
	private Messenger messenger;
	
	@Captor
	private ArgumentCaptor<Email> captor;
	
	private AutoCloseable closeable;
	
	@Spy
	private ArrayList<Integer> list;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	@Test
	void shouldSendMessage() {
		var client = new Client(RANDOM_MESSAGE);
		var template = new Template();
		
		when (templateEngineMock.prepareMessage(client, template))
		.thenReturn(RANDOM_MESSAGE);
		
		messenger.sendMessage(client, template);
		
		verify(templateEngineMock).prepareMessage(client, template);
		
		verify(mailServerMock).send((Email) any(Email.class));
		
	}

	@Test
	void shouldSendMessageWithArgumentMatchers() {
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		
		when(templateEngineMock.prepareMessage((Client) any(Client.class), (Template) any(Template.class))).thenReturn(RANDOM_EMAIL);
		
		
		messenger.sendMessage(client, template);
		
		verify(templateEngineMock).prepareMessage(client, template);
		verify(mailServerMock).send((Email) any(Email.class));
	}
	
	@Test
	public void shouldThrowExceptionWhenTemplateEngineThrowsException() {
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		
		when(templateEngineMock.prepareMessage(client, template)).thenThrow(new IllegalArgumentException());
		
		assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(client, template));
		
		
	}
	
	@Test
	public void shouldSentClientEmailToAddressInEmail() {
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		
		when(templateEngineMock.prepareMessage(client, template)).thenReturn(RANDOM_MESSAGE);
		
		messenger.sendMessage(client, template);
		
		verify(templateEngineMock).prepareMessage(client, template);
		verify(mailServerMock).send(captor.capture());
		assertEquals(client.getEmail(), captor.getValue().getAddress());
		
	}
}
