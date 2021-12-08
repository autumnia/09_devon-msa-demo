package devonboot.poc.pay.repository;

import devonboot.poc.pay.dto.RecordDto;
import devonboot.poc.pay.dto.WalletDto;

import devonboot.poc.pay.model.Account;
import devonboot.poc.pay.model.Record;
import devonboot.poc.pay.model.User;
import devonboot.poc.pay.model.Wallet;
import devonframe.dataaccess.CommonDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PayRepository {

    private CommonDao commonDao;
    public PayRepository(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public Wallet retrieveWalletById(String id) {
        WalletDto walletDto = WalletDto.builder().id(id).build();
        walletDto = commonDao.select("retrieveWallet", walletDto);
        Wallet wallet = wallet = Wallet.builder()
                .id(id)
                .user(User.builder()
                        .id(walletDto.getUserId())
                        .build())
                .balance(walletDto.getBalance())
                .account(Account.builder()
                        .id(walletDto.getAccountId())
                        .build())
                .build();

        return wallet;
    }

    public Wallet retrieveWalletByUser(User user) {
        WalletDto walletDto = WalletDto.builder().userId(user.getId()).build();
        walletDto = commonDao.select("retrieveWalletByUserId", walletDto);
        Wallet wallet = wallet = Wallet.builder()
                .id(walletDto.getId())
                .user(user)
                .balance(walletDto.getBalance())
                .account(Account.builder()
                        .id(walletDto.getAccountId())
                        .build())
                .build();

        return wallet;
    }

    public void updateWallet(Wallet wallet) {
        WalletDto walletDto = WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .accountId(wallet.getAccount().getId())
                .balance(wallet.getBalance())
                .build();
        commonDao.update("updateWallet", walletDto);
    }

    public void createRecord(Record record) {
        RecordDto recordDto = RecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .pointDiff(record.getPointDiff())
                .walletId(record.getWallet().getId())
                .userId(record.getUser().getId())
                .status(record.getStatus().name())
                .build();
        commonDao.insert("createRecord", recordDto);
    }

    public void updateRecord(Record record) {
        RecordDto recordDto = RecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .pointDiff(record.getPointDiff())
                .walletId(record.getWallet().getId())
                .userId(record.getUser().getId())
                .status(record.getStatus().name())
                .build();
        commonDao.insert("updateRecord", recordDto);
    }

    public List<Record> retrieveRecordListByUser(User user) {
        List<RecordDto> recordDtoList = commonDao.selectList("retrieveRecordListByUserId", user.getId());
        List<Record> recordList = new ArrayList<>();

        for(RecordDto dto : recordDtoList) {
            Record record = Record.builder()
                    .id(dto.getId())
                    .amount(dto.getAmount())
                    .pointDiff(dto.getPointDiff())
                    .wallet(Wallet.builder()
                            .id(dto.getWalletId())
                            .build())
                    .user((User.builder()
                            .id(dto.getUserId())
                            .build()))
                    .status(Record.Status.valueOf(dto.getStatus()))
                    .timestamp(dto.getTimestamp())
                    .build();
            recordList.add(record);
        }

        return recordList;
    }
}
