package de.hhu.rhinoshareapp.Representations;

import de.hhu.rhinoshareapp.domain.model.Article;
import de.hhu.rhinoshareapp.domain.model.Lending;
import de.hhu.rhinoshareapp.domain.model.Person;
import de.hhu.rhinoshareapp.domain.service.ArticleRepository;
import de.hhu.rhinoshareapp.domain.service.LendingRepository;
import de.hhu.rhinoshareapp.domain.service.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestRepresentation {
	private UserRepository users;
	private ArticleRepository articles;
	private LendingRepository lendings;
	long borrowID;

	public List<Lending> fillRequest() {
		//suche alle artikel wo die request flag gesetzt ist
		Optional<Person> user = users.findUserByuserID(borrowID);
		List<Lending> filledLendings = new ArrayList<>();
		List<Article> requestedIsTrue = articles.findAllArticleByOwnerAndIsRequested(user.get(), true);
		for (Article article : requestedIsTrue) {
			Lending lending = lendings.findLendingBylendedArticle(article).get();
			filledLendings.add(lending);
		}
		return filledLendings;
	}

	public RequestRepresentation(UserRepository users, ArticleRepository articles, LendingRepository lendings, long borrowID) {
		this.users = users;
		this.articles = articles;
		this.lendings = lendings;
		this.borrowID = borrowID;
	}

	public List<Lending> fillDenies() {
		return lendings.findAllLendingBylendingPersonAndIsDummy(users.findUserByuserID(borrowID).get(), true);
	}

	public List<Lending> fillSaleRequests() {
		List<Lending> saleRequests = new ArrayList<>();
		Optional<Person> user = users.findUserByuserID(borrowID);
		List<Article> articles = this.articles.findAllByOwner(user.get());
		for (Article article : articles) {
			if (lendings.findBylendedArticleAndIsRequestedForSale(article, true).isPresent()) {
				saleRequests.add(lendings.findBylendedArticleAndIsRequestedForSale(article, true).get());

			}
		}
		return saleRequests;
	}
}
