package de.hhu.rhinoshareapp.infrastructure.web;


import de.hhu.rhinoshareapp.controller.article.ArticleController;
import de.hhu.rhinoshareapp.controller.conflict.ConflictController;
import de.hhu.rhinoshareapp.domain.mail.MailService;
import de.hhu.rhinoshareapp.domain.model.Article;
import de.hhu.rhinoshareapp.domain.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    Model m;

    @MockBean
    LendingRepository lendingRepo;

    @MockBean
    UserRepository userRepo;

    @MockBean
    ArticleRepository articleRepo;

    @MockBean
    ImageRepository imageRepo;

    @MockBean
    MailService mailService;

    @MockBean
    ReservationRepository reserveRepo;

    @MockBean
    TransactionRepository transRepo;

    @Test
    public void whenMappingEdit_thenSetNewValuesButKeepOldValuesAsSpecified() throws Exception {
        //Arrange
        Article chainsaw = Article.builder().name("Kettensäge").comment("Sägt super").personID(15).articleID(3).deposit(200).rent(15).available(true).build();
        Article betterChainsaw = Article.builder().name("Bessere Kettensäge").comment("Sägt noch besser!").deposit(250).rent(25).available(false).build();
        Mockito.when(articleRepo.findById(3)).thenReturn(chainsaw);

        //Act
        mvc.perform(post("/article/edit/3")
                .param("name", betterChainsaw.getName())
                .param("comment", betterChainsaw.getComment())
                .param("deposit", "250")
                .param("rent", "25")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/article/3"))
                .andExpect(redirectedUrl("/article/3"));

        //Assert
        assertEquals(chainsaw.getName(), betterChainsaw.getName());
        assertEquals(chainsaw.getComment(), betterChainsaw.getComment());
        assertEquals(chainsaw.getDeposit(), betterChainsaw.getDeposit());
        assertEquals(chainsaw.getRent(), betterChainsaw.getRent());
    }
}