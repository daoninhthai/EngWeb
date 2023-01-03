package com.comittedpeople.englishlearningweb.bootstrap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.comittedpeople.englishlearningweb.domain.AccountAuthority;
import com.comittedpeople.englishlearningweb.domain.ChatRoomMessage;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.domain.UserAccount;
import com.comittedpeople.englishlearningweb.repositories.AccountAuthorityRepository;
import com.comittedpeople.englishlearningweb.repositories.ChatRoomMessageRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarCategoryRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarContentRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarExampleRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarFormRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarNoteRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabCategoryRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabContentRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabLessonRepository;
import com.comittedpeople.englishlearningweb.repositories.UserAccountRepository;

import io.jsonwebtoken.lang.Collections;

@Component
@Transactional
public class Bootstrap implements CommandLineRunner {
	
	private DocVocabCategoryRepository docVocabCategoryRepository;

	private DocVocabLessonRepository docVocabLessonRepository;

	private DocVocabContentRepository docVocabContentRepository;

	private DocGrammarCategoryRepository docGrammarCategoryRepository;

	private DocGrammarExampleRepository docGrammarExampleRepository;

	private DocGrammarNoteRepository docGrammarNoteRepository;

	private DocGrammarContentRepository docGrammarContentRepository;
	private DocGrammarFormRepository docGrammarFormRepository;
	
	private UserAccountRepository userAccountRepository;
	
	private AccountAuthorityRepository authorityRepository;
	
	private ChatRoomMessageRepository chatRoomMessageRepository;
	
	//Lưu lại các user luôn đỡ phải tìm lại.
	private UserAccount adminAccount;
	private UserAccount meAccount;
	private UserAccount weAccount;
	private UserAccount youAccount;
	private UserAccount theyAccount;
	private UserAccount heAccount;
	
	//PasswordEncoder dùng để mã hoá mật khẩu cho user.
	@Autowired
	PasswordEncoder passwordEncoder;

	public Bootstrap(DocVocabCategoryRepository docVocabCategoryRepository,
			DocVocabLessonRepository docVocabLessonRepository, DocVocabContentRepository docVocabContentRepository,
			DocGrammarCategoryRepository docGrammarCategoryRepository,
			DocGrammarExampleRepository docGrammarExampleRepository, DocGrammarNoteRepository docGrammarNoteRepository,
			DocGrammarContentRepository docGrammarContentRepository, DocGrammarFormRepository docGrammarFormRepository,
			UserAccountRepository userAccountRepository, AccountAuthorityRepository authorityRepository,
			ChatRoomMessageRepository chatRoomMessageRepository, PasswordEncoder passwordEncoder) {
		super();
		this.docVocabCategoryRepository = docVocabCategoryRepository;
		this.docVocabLessonRepository = docVocabLessonRepository;
		this.docVocabContentRepository = docVocabContentRepository;
		this.docGrammarCategoryRepository = docGrammarCategoryRepository;
		this.docGrammarExampleRepository = docGrammarExampleRepository;
		this.docGrammarNoteRepository = docGrammarNoteRepository;
		this.docGrammarContentRepository = docGrammarContentRepository;
		this.docGrammarFormRepository = docGrammarFormRepository;
		this.userAccountRepository = userAccountRepository;
		this.authorityRepository = authorityRepository;
		this.chatRoomMessageRepository = chatRoomMessageRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		setupVocabCategories();

		setupVocabLesson();

		setupVocabContent();

		setupGrammarExample();

		setupGrammarNote();
//		
		setupGrammarForm();
//		
		setupGrammarContent();
//		
		setupGrammarCategory();
		
		setupAccountAuthority();
		
		setupUserAccount();
		
		setupChatroomMessage();
	}
	
	private void setupChatroomMessage() {
		
		//Admin.
		ChatRoomMessage messageAdmin = new ChatRoomMessage();
		messageAdmin.setChatContent("Hello ! I'm new !");
		messageAdmin.setMessageSentDate(getLocalDateTimeFromString("2020-06-26 15:45:30.123"));
		messageAdmin.setUserSentAccount(adminAccount);
		
		ChatRoomMessage messageAdmin2 = new ChatRoomMessage();
		messageAdmin2.setChatContent("What's up guys and girls ?");
		messageAdmin2.setMessageSentDate(getLocalDateTimeFromString("2020-06-26 15:45:30.456"));
		messageAdmin2.setUserSentAccount(adminAccount);
		
		adminAccount.getChatRoomMessages().add(messageAdmin);
		adminAccount.getChatRoomMessages().add(messageAdmin2);
		
		chatRoomMessageRepository.save(messageAdmin);
		chatRoomMessageRepository.save(messageAdmin2);
		userAccountRepository.save(adminAccount);
		
		//Me.
		ChatRoomMessage messageMe = new ChatRoomMessage();
		messageMe.setChatContent("Voila ! I'm me ! Hello everyone !");
		messageMe.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 15:59:30.133"));
		messageMe.setUserSentAccount(meAccount);
		
		ChatRoomMessage messageMe2 = new ChatRoomMessage();
		messageMe2.setChatContent("This site is really awesome by the way !");
		messageMe2.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 15:59:30.135"));
		messageMe2.setUserSentAccount(meAccount);
		
		meAccount.getChatRoomMessages().add(messageMe);
		meAccount.getChatRoomMessages().add(messageMe2);
		
		chatRoomMessageRepository.save(messageMe);
		chatRoomMessageRepository.save(messageMe2);
		userAccountRepository.save(meAccount);
		
		//You.
		ChatRoomMessage messageYou = new ChatRoomMessage();
		messageYou.setChatContent("Hi guys ! New here !");
		messageYou.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 15:50:30.110"));
		messageYou.setUserSentAccount(youAccount);
		
		ChatRoomMessage messageYou2 = new ChatRoomMessage();
		messageYou2.setChatContent("Let's improve our English together !");
		messageYou2.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 15:50:54.112"));
		messageYou2.setUserSentAccount(youAccount);
		
		youAccount.getChatRoomMessages().add(messageYou);
		youAccount.getChatRoomMessages().add(messageYou2);
		
		chatRoomMessageRepository.save(messageYou);
		chatRoomMessageRepository.save(messageYou2);
		userAccountRepository.save(youAccount);
		
		//We.
		ChatRoomMessage messageWe = new ChatRoomMessage();
		messageWe.setChatContent("Hello ! How you guys are doing ?");
		messageWe.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 16:45:30.930"));
		messageWe.setUserSentAccount(weAccount);
		
		ChatRoomMessage messageWe2 = new ChatRoomMessage();
		messageWe2.setChatContent("Anyone here has anykind of certificate yet ?");
		messageWe2.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 16:45:40.532"));
		messageWe2.setUserSentAccount(weAccount);
		
		weAccount.getChatRoomMessages().add(messageWe);
		weAccount.getChatRoomMessages().add(messageWe2);
		
		chatRoomMessageRepository.save(messageWe);
		chatRoomMessageRepository.save(messageWe2);
		userAccountRepository.save(weAccount);
		
		//He.
		ChatRoomMessage messageHe = new ChatRoomMessage();
		messageHe.setChatContent("Wow ! There are so many people here !");
		messageHe.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 17:45:30.130"));
		messageHe.setUserSentAccount(heAccount);
		
		ChatRoomMessage messageHe2 = new ChatRoomMessage();
		messageHe2.setChatContent("Committed People is really awesome BTW");
		messageHe2.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 18:41:30.130"));
		messageHe2.setUserSentAccount(heAccount);
		
		heAccount.getChatRoomMessages().add(messageHe);
		heAccount.getChatRoomMessages().add(messageHe2);
		
		chatRoomMessageRepository.save(messageHe);
		chatRoomMessageRepository.save(messageHe2);
		userAccountRepository.save(heAccount);
		
		//They.
		ChatRoomMessage messageThey = new ChatRoomMessage();
		messageThey.setChatContent("Hi admin ! Waiting for people to join !");
		messageThey.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 00:00:00.000"));
		messageThey.setUserSentAccount(theyAccount);
		
		ChatRoomMessage messageThey2 = new ChatRoomMessage();
		messageThey2.setChatContent("I'll be waiting from dust till dawn ...");
		messageThey2.setMessageSentDate(getLocalDateTimeFromString("2020-06-27 01:20:00.000"));
		messageThey2.setUserSentAccount(theyAccount);
		
		theyAccount.getChatRoomMessages().add(messageThey);
		theyAccount.getChatRoomMessages().add(messageThey2);
		
		chatRoomMessageRepository.save(messageThey);
		chatRoomMessageRepository.save(messageThey2);
		userAccountRepository.save(theyAccount);
	}
	
	private LocalDateTime getLocalDateTimeFromString (String dateTime) {
		//DateTimeFormatter để parse từ String sang LocalDateTime.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime result = LocalDateTime.parse(dateTime, formatter);
		return result;
	}
	
	private void setupAccountAuthority() {
		AccountAuthority admin = new AccountAuthority();
		admin.setId(1L);
		admin.setName("ROLE_ADMIN");
		
		AccountAuthority user = new AccountAuthority();
		user.setId(2L);
		user.setName("ROLE_USER");
		
		authorityRepository.save(admin);
		authorityRepository.save(user);
		
	}
	
	private void setupUserAccount() {
		
		AccountAuthority admin = authorityRepository.findByName("ROLE_ADMIN");
		AccountAuthority user = authorityRepository.findByName("ROLE_USER");
		
		adminAccount = new UserAccount();
		
		adminAccount.setId(1L);
		adminAccount.setUsername("admin");
		adminAccount.setPassword(passwordEncoder.encode("123"));
		adminAccount.setEnabled(true);
		adminAccount.setEmail("committedpeople@gmail.com");
		adminAccount.setDisplayname("Admin Committed");
		adminAccount.setReminder(1);
		adminAccount.setReminderMonday((byte) 12);
		adminAccount.setReminderTuesday((byte) 11);
		adminAccount.setReminderWednesday((byte) 10);
		adminAccount.setReminderThursday((byte) 9);
		adminAccount.setReminderFriday((byte) 8);
		adminAccount.setReminderSaturday((byte) 7);
		adminAccount.setReminderSunday((byte) -1);
		
		adminAccount.getAuthorities().addAll(Arrays.asList(admin,user));
		
		userAccountRepository.save(adminAccount);
		
		//Tạo 1 user account.
		meAccount = new UserAccount();
		meAccount.setId(2L);
		meAccount.setUsername("me");
		meAccount.setPassword(passwordEncoder.encode("me"));
		meAccount.setEnabled(true);
		meAccount.setEmail("thaimeo1131@gmail.com");
		meAccount.setDisplayname("Me Committed");
		meAccount.setReminder(2);
		meAccount.setReminderMonday((byte) 7);
		meAccount.setReminderTuesday((byte) 18);
		meAccount.setReminderWednesday((byte) 19);
		meAccount.setReminderThursday((byte) 11);
		meAccount.setReminderFriday((byte) 6);
		meAccount.setReminderSaturday((byte) -1);
		meAccount.setReminderSunday((byte) -1);
		//Sau khi tạo xong UserAccount, gán Role cho User vừa tạo.
		meAccount.getAuthorities().addAll(Arrays.asList(user));
		
		//Tạo 1 user account.
		youAccount = new UserAccount();
		youAccount.setId(3L);
		youAccount.setUsername("you");
		youAccount.setPassword(passwordEncoder.encode("you"));
		youAccount.setEnabled(true);
		youAccount.setEmail("thaimeo123@gmail.com");
		youAccount.setDisplayname("You Committed");
		youAccount.setReminder(3);
		youAccount.setReminderMonday((byte) 5);
		youAccount.setReminderTuesday((byte) 8);
		youAccount.setReminderWednesday((byte) 9);
		youAccount.setReminderThursday((byte) 10);
		youAccount.setReminderFriday((byte) 11);
		youAccount.setReminderSaturday((byte) 6);
		youAccount.setReminderSunday((byte) 7);
		//Sau khi tạo xong UserAccount, gán Role cho User vừa tạo.
		youAccount.getAuthorities().addAll(Arrays.asList(user));
		
		//Tạo 1 user account.
		weAccount = new UserAccount();
		weAccount.setId(4L);
		weAccount.setUsername("we");
		weAccount.setPassword(passwordEncoder.encode("we"));
		weAccount.setEnabled(true);
		weAccount.setEmail("committedpeople@gmail.com");
		weAccount.setDisplayname("We Committed");
		weAccount.setReminder(5);
		weAccount.setReminderMonday((byte) 6);
		weAccount.setReminderTuesday((byte) -1);
		weAccount.setReminderWednesday((byte) -1);
		weAccount.setReminderThursday((byte) -1);
		weAccount.setReminderFriday((byte) -1);
		weAccount.setReminderSaturday((byte) -1);
		weAccount.setReminderSunday((byte) -1);
		//Sau khi tạo xong UserAccount, gán Role cho User vừa tạo.
		weAccount.getAuthorities().addAll(Arrays.asList(user));
		
		//Tạo 1 user account.
		heAccount = new UserAccount();
		heAccount.setId(5L);
		heAccount.setUsername("he");
		heAccount.setPassword(passwordEncoder.encode("he"));
		heAccount.setEnabled(true);
		heAccount.setEmail("committedpeople@gmail.com");
		heAccount.setDisplayname("He Committed");
		heAccount.setReminder(1);
		heAccount.setReminderMonday((byte) -1);
		heAccount.setReminderTuesday((byte) -1);
		heAccount.setReminderWednesday((byte) -1);
		heAccount.setReminderThursday((byte) -1);
		heAccount.setReminderFriday((byte) -1);
		heAccount.setReminderSaturday((byte) 7);
		heAccount.setReminderSunday((byte) 7);
		//Sau khi tạo xong UserAccount, gán Role cho User vừa tạo.
		heAccount.getAuthorities().addAll(Arrays.asList(user));
		
		//Tạo 1 user account.
		theyAccount = new UserAccount();
		theyAccount.setId(6L);
		theyAccount.setUsername("they");
		theyAccount.setPassword(passwordEncoder.encode("they"));
		theyAccount.setEnabled(true);
		theyAccount.setEmail("committedpeople@gmail.com");
		theyAccount.setDisplayname("They Committed");
		theyAccount.setReminder(0);
		theyAccount.setReminderMonday((byte) -1);
		theyAccount.setReminderTuesday((byte) -1);
		theyAccount.setReminderWednesday((byte) -1);
		theyAccount.setReminderThursday((byte) -1);
		theyAccount.setReminderFriday((byte) -1);
		theyAccount.setReminderSaturday((byte) -1);
		theyAccount.setReminderSunday((byte) -1);
		//Sau khi tạo xong UserAccount, gán Role cho User vừa tạo.
		theyAccount.getAuthorities().addAll(Arrays.asList(user));
		
		//Lưu lại các user vừa tạo.
		meAccount = userAccountRepository.save(meAccount);
		youAccount = userAccountRepository.save(youAccount);
		weAccount = userAccountRepository.save(weAccount);
		heAccount = userAccountRepository.save(heAccount);
		theyAccount = userAccountRepository.save(theyAccount);
		
		//Thêm 2 chiều cho cả các phân quyền. Các phân quyền cũng phải nắm được thông tin User thuộc.
		admin.getUsers().add(adminAccount);
		user.getUsers().addAll(Arrays.asList(meAccount, youAccount, weAccount, heAccount, theyAccount, adminAccount));

		//Lưu lại các phân quyền.
		authorityRepository.save(admin);
		authorityRepository.save(user);
		
		System.out.println("User : " + userAccountRepository.count());
	}

	private void setupGrammarExample() {
		DocGrammarExample ex1 = getGrammarExample(1L,
				"The man on the left is <b>taller</b> than the man on the right <br/> The man on the right is <b>shorter</b> than the man on the left",
				"https://i.imgur.com/znoyZE7.png");
		DocGrammarExample ex2 = getGrammarExample(2L,
				"The car is <b>faster</b> than the bike <br/> The bike is <b>slower</b> than the car",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRwDHK1tQpmqcvesnhkkvc3nLGS2hD4GFOi7T-Jjl12NIMUxZIn&usqp=CAU");
		DocGrammarExample ex3 = getGrammarExample(3L,
				"The man on the left is <b>taller</b> than the man on the right <br/> The man on the right is <b>shorter</b> than the man on the left",
				"https://i.imgur.com/znoyZE7.png");
		DocGrammarExample ex4 = getGrammarExample(4L,
				"The car is <b>faster</b> than the bike <br/> The bike is <b>slower</b> than the car",
				"https://image.smythstoys.com/original/desktop/158328.jpg");
		DocGrammarExample ex5 = getGrammarExample(5L, "The Amazon River is <b>the widest</b> river in the world. ",
				"https://www.seabourn.com/content/dam/sbn/inventory-assets/ports/264/CRUISING-THE-AMAZON-RIVER.jpg.image.750.563.low.jpg");
		DocGrammarExample ex6 = getGrammarExample(6L,
				"Mount Everest is <b>the highest</b> mountain in the word. (But it isn't the tallest mountain in the world.)",
				"https://cdn.britannica.com/s:800x450,c:crop/72/143172-138-5F42EB87/mountains-slopes-Old.jpg");

		docGrammarExampleRepository.save(ex1);
		docGrammarExampleRepository.save(ex2);
		docGrammarExampleRepository.save(ex3);
		docGrammarExampleRepository.save(ex4);
		docGrammarExampleRepository.save(ex5);
		docGrammarExampleRepository.save(ex6);
	}

	private void setupGrammarNote() {
		DocGrammarNote note1 = getGrammarNote(1L,
				"Have you noticed that when we are comparing two things like this we put than between the adjective and the thing being compared. ");
		DocGrammarNote note2 = getGrammarNote(2L,
				"A lot of people write \"then\" instead of \"than\"- avoid it as it's simply not correct, and let's face it, you would be marked down in a test or exam.");
		DocGrammarNote note3 = getGrammarNote(3L,
				"For every rule, there is at least one exception and there are also irregular adjectives that you need to memorise as you come across them. ");
		DocGrammarNote note4 = getGrammarNote(4L, "'good' becomes 'better' ");
		DocGrammarNote note5 = getGrammarNote(5L, " Have you noticed that these superlatives are preceded by 'the'.");
		DocGrammarNote note6 = getGrammarNote(6L, "Don't forget that some adjectives are irregular");

		docGrammarNoteRepository.save(note1);
		docGrammarNoteRepository.save(note2);
		docGrammarNoteRepository.save(note3);
		docGrammarNoteRepository.save(note4);
		docGrammarNoteRepository.save(note5);
		docGrammarNoteRepository.save(note6);
	}

	private void setupGrammarForm() {

		DocGrammarExample ex1 = docGrammarExampleRepository.getOne(1L);
		DocGrammarExample ex2 = docGrammarExampleRepository.getOne(2L);
		DocGrammarExample ex3 = docGrammarExampleRepository.getOne(3L);
		DocGrammarExample ex4 = docGrammarExampleRepository.getOne(4L);
		DocGrammarExample ex5 = docGrammarExampleRepository.getOne(5L);
		DocGrammarExample ex6 = docGrammarExampleRepository.getOne(6L);

		List<DocGrammarExample> docGrammarExamples1 = Arrays.asList(ex1, ex2); // ,
		List<DocGrammarExample> docGrammarExamples2 = Arrays.asList(ex3, ex4);

		DocGrammarNote note1 = docGrammarNoteRepository.getOne(1L);
		DocGrammarNote note2 = docGrammarNoteRepository.getOne(2L);
		DocGrammarNote note3 = docGrammarNoteRepository.getOne(3L);
		DocGrammarNote note4 = docGrammarNoteRepository.getOne(4L);
		DocGrammarNote note5 = docGrammarNoteRepository.getOne(5L);
		DocGrammarNote note6 = docGrammarNoteRepository.getOne(6L);

		List<DocGrammarNote> grammarNotes1 = Arrays.asList(note1, note2);
		List<DocGrammarNote> grammarNotes2 = Arrays.asList(note3, note4);

		DocGrammarForm form = getGrammarForm(1L, "FORMING THE COMPARATIVE:", "Words of one syllable ending in e",
				"Add -r to the end of word", "Wide - Wider", docGrammarExamples1, grammarNotes1);

		DocGrammarForm form2 = getGrammarForm(2L, "FORMING THE COMPARATIVE 2:", "Words of one syllable ending in e",
				"Add -r to the end of word", "Wide - Wider", docGrammarExamples2, grammarNotes2);

		DocGrammarForm form3 = getGrammarForm(3L, "FORMING THE SUPERLATIVE:", "Words of one syllable ending in e",
				"Add -st to the end of the word.", "wide - widest", Arrays.asList(ex5), Arrays.asList(note5));

		DocGrammarForm form4 = getGrammarForm(4L, "FORMING THE SUPERLATIVE 2:", "Words of one syllable ending in e",
				"Add -r to the end of word", "Wide - Wider", Arrays.asList(ex6), Arrays.asList(note6));

		docGrammarFormRepository.save(form);
		docGrammarFormRepository.save(form2);
		docGrammarFormRepository.save(form3);
		docGrammarFormRepository.save(form4);
	}

	private void setupGrammarContent() {

		DocGrammarForm form = docGrammarFormRepository.getOne(1L);
		DocGrammarForm form2 = docGrammarFormRepository.getOne(2L);
		DocGrammarForm form3 = docGrammarFormRepository.getOne(3L);
		DocGrammarForm form4 = docGrammarFormRepository.getOne(4L);

		List<DocGrammarForm> grammarForms = Arrays.asList(form, form2);
		List<DocGrammarForm> grammarForms2 = Arrays.asList(form3, form4);

		DocGrammarContent content1 = getGrammarContent(1L, "Comparative",
				"When we compare things, people or even ideas we look at what makes them different from each other.\r\n"
						+ "\r\n"
						+ "Comparative adjectives are used to show what quality one thing has more or less than the other. They normally come before any other adjectives. ",
				grammarForms);
		DocGrammarContent content2 = getGrammarContent(2L, "Superlative",
				"The superlative is used to say what thing, person, or idea has the most of a particular quality within a group or of its kind. Superlative adjectives normally come before any other adjectives.",
				grammarForms2);

		docGrammarContentRepository.save(content1);
		docGrammarContentRepository.save(content2);
	}

	private void setupGrammarCategory() {
		DocGrammarContent content1 = docGrammarContentRepository.getOne(1L);
		DocGrammarContent content2 = docGrammarContentRepository.getOne(2L);

		List<DocGrammarContent> contents = Arrays.asList(content1, content2);

		DocGrammarCategory category = getGrammarCategory(1L, "Adjective", "It's adjective", contents);
		
		docGrammarCategoryRepository.save(category);
	}

	private void setupVocabContent() {

		DocVocabLesson lesson1 = docVocabLessonRepository.findById(1L).get();
		DocVocabLesson lesson2 = docVocabLessonRepository.findById(2L).get();
		DocVocabLesson lesson3 = docVocabLessonRepository.getOne(3L);
		DocVocabLesson lesson4 = docVocabLessonRepository.getOne(4L);
		DocVocabLesson lesson5 = docVocabLessonRepository.getOne(5L);
		DocVocabLesson lesson6 = docVocabLessonRepository.getOne(6L);

		DocVocabContent content1lesson1 = new DocVocabContent();
		content1lesson1.setContent("Abide by");
		content1lesson1.setDescription("v. to comply with, to conform: tuân thủ, tôn trọng");
		content1lesson1.setId(1L);
		content1lesson1.setLesson(lesson1);
		content1lesson1.setSpelling("/əˈbaɪd/");
		content1lesson1.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/a/abi/abide/abide__gb_1.mp3");

		DocVocabContent content2lesson1 = new DocVocabContent();
		content2lesson1.setContent("Determine");
		content2lesson1.setDescription("facts about something; to calculate something exactly");
		content2lesson1.setId(2L);
		content2lesson1.setLesson(lesson1);
		content2lesson1.setSpelling("/dɪˈtɜːmɪn/");
		content2lesson1.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/d/det/deter/determine__gb_1.mp3");

		DocVocabContent content1lesson2 = new DocVocabContent();
		content1lesson2.setContent("Success");
		content1lesson2.setDescription("[uncountable] the fact that you have achieved something that you want and have been trying to do or get ");
		content1lesson2.setId(3L);
		content1lesson2.setLesson(lesson2);
		content1lesson2.setSpelling("/səkˈses/");
		content1lesson2.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/us_pron/s/suc/succe/success__us_2.mp3");

		DocVocabContent content2lesson2 = new DocVocabContent();
		content2lesson2.setContent("Extraordinary");
		content2lesson2.setDescription("unexpected, surprising or strange");
		content2lesson2.setId(4L);
		content2lesson2.setLesson(lesson2);
		content2lesson2.setSpelling("/ɪkˈstrɔːdnri/");
		content2lesson2.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/e/ext/extra/extraordinary__gb_1.mp3");

		DocVocabContent content1lesson3 = new DocVocabContent();
		content1lesson3.setContent("inspire");
		content1lesson3.setDescription("to give somebody the desire, confidence or enthusiasm to do something well");
		content1lesson3.setId(5L);
		content1lesson3.setLesson(lesson3);
		content1lesson3.setSpelling("/ɪnˈspaɪə(r)/");
		content1lesson3.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/i/ins/inspi/inspire__gb_1.mp3");

		DocVocabContent content2lesson3 = new DocVocabContent();
		content2lesson3.setContent("innovate");
		content2lesson3.setDescription("to introduce new things, ideas or ways of doing something");
		content2lesson3.setId(6L);
		content2lesson3.setLesson(lesson3);
		content2lesson3.setSpelling("/ˈɪnəveɪt/");
		content2lesson3.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/i/inn/innov/innovate__gb_1.mp3");

		DocVocabContent content1lesson4 = new DocVocabContent();
		content1lesson4.setContent("brilliant");
		content1lesson4.setDescription("extremely clever or impressive");
		content1lesson4.setId(7L);
		content1lesson4.setLesson(lesson4);
		content1lesson4.setSpelling("/ˈbrɪliənt/");
		content1lesson4.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/b/bri/brill/brilliant__gb_1.mp3");

		DocVocabContent content2lesson4 = new DocVocabContent();
		content2lesson4.setContent("exceptional");
		content2lesson4.setDescription("unusually good");
		content2lesson4.setId(8L);
		content2lesson4.setLesson(lesson4);
		content2lesson4.setSpelling("/ɪkˈsepʃənl/");
		content2lesson4.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/e/exc/excep/exceptional__gb_1.mp3");

		DocVocabContent content1lesson5 = new DocVocabContent();
		content1lesson5.setContent("creative");
		content1lesson5.setDescription(
				"involving the use of skill and the imagination to produce something new or a work of art");
		content1lesson5.setId(9L);
		content1lesson5.setLesson(lesson5);
		content1lesson5.setSpelling("/kriˈeɪtɪv/");
		content1lesson5.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/c/cre/creat/creative__gb_3.mp3");

		DocVocabContent content2lesson5 = new DocVocabContent();
		content2lesson5.setContent("fascinating");
		content2lesson5.setDescription("extremely interesting and attractive");
		content2lesson5.setId(10L);
		content2lesson5.setLesson(lesson5);
		content2lesson5.setSpelling("/ˈfæsɪneɪtɪŋ/");
		content2lesson5.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/f/fas/fasci/fascinating__gb_1.mp3");

		DocVocabContent content1lesson6 = new DocVocabContent();
		content1lesson6.setContent("marvellous");
		content1lesson6.setDescription("extremely good; wonderful");
		content1lesson6.setId(11L);
		content1lesson6.setLesson(lesson6);
		content1lesson6.setSpelling("/ˈmɑːvələs/");
		content1lesson6.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/m/mar/marve/marvellous__gb_1.mp3");

		DocVocabContent content2lesson6 = new DocVocabContent();
		content2lesson6.setContent("complete");
		content2lesson6.setDescription("including all the parts, etc. that are necessary; whole");
		content2lesson6.setId(12L);
		content2lesson6.setLesson(lesson6);
		content2lesson6.setSpelling("/kəmˈpliːt/");
		content2lesson6.setSpellingAudioURL(
				"https://www.oxfordlearnersdictionaries.com/media/english/uk_pron/c/com/compl/complete__gb_2.mp3");

		// Sau khi thêm xong từ vựng thì ta cập nhật lại lesson với từ vựng mới.
		lesson1.setVocabs(new HashSet<>(Arrays.asList(content1lesson1, content2lesson1)));
		lesson2.setVocabs(new HashSet<>(Arrays.asList(content1lesson2, content2lesson2)));
		lesson3.setVocabs(new HashSet<>(Arrays.asList(content1lesson3, content2lesson3)));
		lesson4.setVocabs(new HashSet<>(Arrays.asList(content1lesson4, content2lesson4)));
		lesson5.setVocabs(new HashSet<>(Arrays.asList(content1lesson5, content2lesson5)));
		lesson6.setVocabs(new HashSet<>(Arrays.asList(content1lesson6, content2lesson6)));

		docVocabContentRepository.save(content1lesson1);
		docVocabContentRepository.save(content2lesson1);
		docVocabContentRepository.save(content1lesson2);
		docVocabContentRepository.save(content2lesson2);
		docVocabContentRepository.save(content1lesson3);
		docVocabContentRepository.save(content2lesson3);
		docVocabContentRepository.save(content1lesson4);
		docVocabContentRepository.save(content2lesson4);
		docVocabContentRepository.save(content1lesson5);
		docVocabContentRepository.save(content2lesson5);
		docVocabContentRepository.save(content1lesson6);
		docVocabContentRepository.save(content2lesson6);

		docVocabLessonRepository.save(lesson1);
		docVocabLessonRepository.save(lesson2);
		docVocabLessonRepository.save(lesson3);
		docVocabLessonRepository.save(lesson4);
		docVocabLessonRepository.save(lesson5);
		docVocabLessonRepository.save(lesson6);

		System.out.println("DocVocabContent loaded : " + docVocabContentRepository.count());
//		System.out.println("Lesson1 : " + lesson1.getTitle());
//		System.out.println("Lesson2: " + lesson2.getTitle());

	}

	private void setupVocabLesson() {
		DocVocabCategory cat01 = docVocabCategoryRepository.getOne(1L);
		DocVocabCategory cat02 = docVocabCategoryRepository.getOne(2L);
		DocVocabCategory cat03 = docVocabCategoryRepository.getOne(3L);

		DocVocabLesson lesson1Cat01 = new DocVocabLesson();
		lesson1Cat01.setId(1L);
		lesson1Cat01.setTitle("Contract");
		lesson1Cat01.setImageURL("https://upload.wikimedia.org/wikipedia/commons/8/8a/ContractLaw.jpg");
		lesson1Cat01.setCategory(cat01);

		DocVocabLesson lesson2Cat01 = new DocVocabLesson();
		lesson2Cat01.setId(2L);
		lesson2Cat01.setTitle("Marketing");
		lesson2Cat01.setImageURL("https://gtvseo.com/images/marketing/marketing-vs-advertising.jpg");
		lesson2Cat01.setCategory(cat01);

		DocVocabLesson lesson1Cat02 = new DocVocabLesson();
		lesson1Cat02.setId(3L);
		lesson1Cat02.setTitle("Warranties");
		lesson1Cat02.setImageURL("data:image/jpeg;base64,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");
		lesson1Cat02.setCategory(cat02);

		DocVocabLesson lesson2Cat02 = new DocVocabLesson();
		lesson2Cat02.setId(4L);
		lesson2Cat02.setTitle("School");
		lesson2Cat02.setImageURL("data:image/jpeg;base64,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");
		lesson2Cat02.setCategory(cat02);

		DocVocabLesson lesson1Cat03 = new DocVocabLesson();
		lesson1Cat03.setId(5L);
		lesson1Cat03.setTitle("Correspondence ");
		lesson1Cat03.setImageURL("https://tienganhmoingay.com/static/Vocabulary/images/word_images/correspondence.jpg");
		lesson1Cat03.setCategory(cat03);

		DocVocabLesson lesson2Cat03 = new DocVocabLesson();
		lesson2Cat03.setId(6L);
		lesson2Cat03.setTitle("Conferences ");
		lesson2Cat03.setImageURL("data:image/jpeg;base64,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");
		lesson2Cat03.setCategory(cat03);

		docVocabLessonRepository.save(lesson1Cat01);
		docVocabLessonRepository.save(lesson2Cat01);	
		docVocabLessonRepository.save(lesson1Cat02);
		docVocabLessonRepository.save(lesson2Cat02);
		docVocabLessonRepository.save(lesson1Cat03);
		docVocabLessonRepository.save(lesson2Cat03);

		System.out.println("DocVocabLesson loaded : " + docVocabLessonRepository.count());
//		System.out.println("Cat 1 : " + cat01.getTitle());
	}

	private void setupVocabCategories() {
		DocVocabCategory general = new DocVocabCategory();
		general.setTitle("General topic");
		general.setDescription("This is an general topic");

		DocVocabCategory toiecSixHundred = new DocVocabCategory();
		toiecSixHundred.setTitle("600 words TOIEC HCR");
		toiecSixHundred.setDescription("600 words TOIEC HCR");

		DocVocabCategory longManVocab = new DocVocabCategory();
		longManVocab.setTitle("Longman Vocabulary");
		longManVocab.setDescription("Subset of Longman Vocabulary");

		docVocabCategoryRepository.save(general);
		docVocabCategoryRepository.save(toiecSixHundred);
		docVocabCategoryRepository.save(longManVocab);

		System.out.println("DocVocabCategory loaded : " + docVocabCategoryRepository.count());
	}

	private DocGrammarCategory getGrammarCategory(Long id, String title, String description,
			List<DocGrammarContent> grammars) {
		DocGrammarCategory category = new DocGrammarCategory();

		for (DocGrammarContent content : grammars) {
			content.setCategory(category);
		}

		category.setId(id);
		category.setTitle(title);
		category.setDescription(description);
		category.setGrammars(new HashSet<DocGrammarContent>(grammars));

		return category;
	}

	private DocGrammarContent getGrammarContent(Long id, String title, String description, List<DocGrammarForm> forms) {
		DocGrammarContent grammarContent = new DocGrammarContent();

		for (DocGrammarForm form : forms) {
			form.setDocGrammarContent(grammarContent);
		}

		grammarContent.setTitle(title);
		grammarContent.setId(id);
		grammarContent.setDescription(description);
		grammarContent.setForms(new HashSet<>(forms));

		return grammarContent;

	}

	private DocGrammarForm getGrammarForm(Long ID, String title, String usage, String useCase, String how,
			List<DocGrammarExample> examples, List<DocGrammarNote> notes) {
		DocGrammarForm form = new DocGrammarForm();

		for (DocGrammarExample example : examples) {
			example.setDocGrammarForm(form);
		}
		for (DocGrammarNote note : notes) {
			note.setDocGrammarForm(form);
		}

		form.setExamples(new HashSet<DocGrammarExample>(examples));
		form.setHow(how);
		form.setId(ID);
		form.setNotes(new HashSet<DocGrammarNote>(notes));
		form.setTitle(title);
		form.setUsage(usage);
		form.setUseCase(useCase);

		return form;
	}

	private DocGrammarExample getGrammarExample(Long ID, String content, String imageURL) {
		DocGrammarExample res = new DocGrammarExample();

		res.setId(ID);
		res.setContent(content);
		res.setImageURL(imageURL);

		return res;

	}

	private DocGrammarNote getGrammarNote(Long id, String content) {
		DocGrammarNote result = new DocGrammarNote();
		result.setId(id);
		result.setContent(content);

		return result;
	}

}
