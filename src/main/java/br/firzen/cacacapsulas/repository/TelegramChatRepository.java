package br.firzen.cacacapsulas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.firzen.cacacapsulas.model.TelegramChat;

@Repository
public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long>{
	public TelegramChat findByChatId(Long id);

	public void deleteByChatId(long chatId);
}
