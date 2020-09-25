package com.sbs.java.blog.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {

	// req 통해 가져온 paramName 비었는지 유무 확인
	public static boolean empty(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return empty(paramValue);
	}

	// 값 비었는지 유무 확인
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		return true;
	}

	public static boolean isNum(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return isNum(paramValue);
	}

	public static boolean isNum(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Long) {
			return true;
		} else if (obj instanceof Integer) {
			return true;
		} else if (obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}

	// req 통해 가져온 paramName 비었는지 유무 확인
	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}

	// 에러 문구 출력
	public static void printEx(String errName, HttpServletResponse resp, Exception e) {
		try {
			resp.getWriter()
					.append("<h1 style='color:red; font-weight:bold; text-align:left;'>[에러 : " + errName + "]</h1>");

			resp.getWriter().append("<pre style='text-align:left; font-weight:bold; font-size:1.3rem;'>");
			e.printStackTrace(resp.getWriter());
			resp.getWriter().append("</pre>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getString(HttpServletRequest req, String paramName) {
		return req.getParameter(paramName);
	}
	
	public static String getUriEncoded(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String getString(HttpServletRequest req, String paramName, String elseValue) {
		if (req.getParameter(paramName) == null) {
			return elseValue;
		}

		if (req.getParameter(paramName).trim().length() == 0) {
			return elseValue;
		}

		return getString(req, paramName);
	}
	
	// 게시물 삭제 관련 성공여부 확인
	public static boolean isSuccess(Map<String, Object> rs) {
		return ((String) rs.get("resultCode")).startsWith("S-");
	}
	
	public static String getNewUriRemoved(String uri, String paramName) {
		String deleteStrStarts = paramName + "=";
		int delStartPos = uri.indexOf(deleteStrStarts);

		if (delStartPos != -1) {
			int delEndPos = uri.indexOf("&", delStartPos);

			if (delEndPos != -1) {
				delEndPos++;
				uri = uri.substring(0, delStartPos) + uri.substring(delEndPos, uri.length());
			} else {
				uri = uri.substring(0, delStartPos);
			}
		}

		if (uri.charAt(uri.length() - 1) == '?') {
			uri = uri.substring(0, uri.length() - 1);
		}

		if (uri.charAt(uri.length() - 1) == '&') {
			uri = uri.substring(0, uri.length() - 1);
		}

		return uri;
	}
	
	public static String getNewUri(String uri, String paramName, String paramValue) {
		uri = getNewUriRemoved(uri, paramName);

		if (uri.contains("?")) {
			uri += "&" + paramName + "=" + paramValue;
		} else {
			uri += "?" + paramName + "=" + paramValue;
		}

		uri = uri.replace("?&", "?");

		return uri;
	}

	public static String getNewUriAndEncoded(String uri, String paramName, String pramValue) {
		return getUriEncoded(getNewUri(uri, paramName, pramValue));
	}
	
	public static int sendMail(String smtpServerId, String smtpServerPw, String from, String fromName, String to,
			String title, String body) {		
		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "587");

		Authenticator auth = new MailAuth(smtpServerId, smtpServerPw);

		Session session = Session.getDefaultInstance(prop, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSentDate(new Date());

			msg.setFrom(new InternetAddress(from, fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title, "UTF-8");
			msg.setContent(body, "text/html; charset=UTF-8");

			Transport.send(msg);

		} catch (AddressException ae) {
			System.out.println("AddressException : " + ae.getMessage());
			return -1;
		} catch (MessagingException me) {
			System.out.println("MessagingException : " + me.getMessage());
			return -2;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			return -3;
		}

		return 1;
	}
	
	// 임시 비밀번호 발급 관련 난수 발생기
	public static String numberGen() {
		
		Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
        // len : 난수 자릿수
        int len = 4;
        // dupCd : 난수의 중복 허용 여부 ( 1 허용 / 2 비 허용 )
        int dupCd = 1;
        
        for(int i = 0; i < len; i++) {
            
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;	
	}	
	
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}

	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception ex) {
			return "";
		}
	}
	
	// 비밀번호 변경 기간관련 안내
	public static long getTime(String currentDateTime, String lastDateTime) {
		
		long calDateDays = 0;
		
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // mm으로 할 경우 분을 의미한다.
	        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
	        Date current = format.parse(currentDateTime);
	        Date last = format.parse(lastDateTime);
	        
	        // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
	        // 연산결과 -950400000. long type 으로 return 된다.
	        long calDate = current.getTime() - last.getTime(); 
	        
	        // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다. 
	        // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
        	calDateDays = calDate / ( 24*60*60*1000); 
	 
	        calDateDays = Math.abs(calDateDays);

        }
        catch(ParseException e)
        {
            // 예외 처리
        }
     
        return calDateDays;
	}

	//현재 날짜 가져오기
	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format1.format(System.currentTimeMillis());

		return dateStr;
	}
		
}