import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

// Transaction class to represent energy trade data
class Transaction {
    String sender;
    String receiver;
    double energyAmount;
    long timestamp;

    public Transaction(String sender, String receiver, double energyAmount) {
        this.sender = sender;
        this.receiver = receiver;
        this.energyAmount = energyAmount;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transaction{sender='" + sender + "', receiver='" + receiver + "', energyAmount=" + energyAmount + ", timestamp=" + timestamp + "}";
    }
}

// Block class that contains a transaction and its hash
class Block {
    String previousHash;
    String hash;
    Transaction transaction;
    long timestamp;

    public Block(String previousHash, Transaction transaction) {
        this.previousHash = previousHash;
        this.transaction = transaction;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    // Calculate hash of the block using SHA-256
    public String calculateHash() {
        String data = previousHash + transaction.toString() + timestamp;
        return applySha256(data);
    }

    // Helper method to apply SHA-256 hash
    private static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Block{previousHash='" + previousHash + "', hash='" + hash + "', transaction=" + transaction + ", timestamp=" + timestamp + "}";
    }
}

// Blockchain class that holds the chain of blocks
class Blockchain {
    List<Block> chain;
    public Blockchain() {
        chain = new ArrayList<>();
        // Create the genesis block (first block)
        Transaction genesisTransaction = new Transaction("Producer", "Consumer", 10.5);
        Block genesisBlock = new Block("0", genesisTransaction);
        chain.add(genesisBlock);
    }

    // Add a block to the blockchain
    public void addBlock(Transaction transaction) {
        Block lastBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(lastBlock.hash, transaction);
        chain.add(newBlock);
    }

    // Print the blockchain
    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println(block);
        }
    }
}

// Main class to simulate the energy trading system
public class EnergyTradingBlockchain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        // Simulating energy trade transactions
        System.out.println("Energy Trading Blockchain\n");
        
        // First transaction: Producer sells 5.5 units of energy to Consumer
        Transaction transaction1 = new Transaction("Producer", "Consumer", 5.5);
        blockchain.addBlock(transaction1);

        // Second transaction: Producer sells 3.0 units of energy to Consumer
        Transaction transaction2 = new Transaction("Producer", "Consumer", 3.0);
        blockchain.addBlock(transaction2);

        // Print the blockchain to show all the transactions and blocks
        blockchain.printBlockchain();
    }
}
