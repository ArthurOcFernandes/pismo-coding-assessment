CREATE INDEX idx_transactions_account_id ON transactions (account_id);
CREATE UNIQUE INDEX idx_accounts_document_number ON accounts (document_number);