package com.joaotech.chatservice.service;

import com.github.javafaker.Faker;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CSVService {

    private final RoomService roomService;

    public void generate() throws FileNotFoundException {

        final int CSV_SIZE = 2000;

        Faker faker = new Faker(new Locale("pt-BR"));

        PrintWriter printWriter = new PrintWriter(getFileName());

        for (int i = 0; i < CSV_SIZE; i++) {

            String line = generateRoom(faker);

            printWriter.println(line);

        }

        printWriter.flush();

        printWriter.close();

    }

    private String generateRoom(Faker faker) {

        final int TOKEN_SIZE = 10;

        String senderToken = RandomStringUtils.randomAlphanumeric(TOKEN_SIZE);

        String recipientToken = RandomStringUtils.randomAlphanumeric(TOKEN_SIZE);

        OpenRoomVO openRoomVO = OpenRoomVO.builder()
                .sender(
                        UserVO.builder()
                                .token(senderToken)
                                .name(faker.name().name())
                                .build()
                )
                .recipient(
                        UserVO.builder()
                                .token(recipientToken)
                                .name(faker.name().name())
                                .build()
                )
                .build();

        String roomId = roomService.open(openRoomVO);

        return String.join(",", roomId, senderToken, recipientToken);

    }

    private String getFileName() {

        final String ENVIRONMENT_VARIABLE_NAME = "CSV_ROOMS_NAME";

        final String fileName = System.getenv(ENVIRONMENT_VARIABLE_NAME);

        if (fileName == null) {
            throw new RuntimeException("You need to set the environment variable " + ENVIRONMENT_VARIABLE_NAME);
        }

        return fileName;

    }

}
