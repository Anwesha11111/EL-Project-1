from web3 import Web3
from solcx import compile_standard, install_solc

# Setup Web3
w3 = Web3(Web3.HTTPProvider("http://127.0.0.1:8545"))  # Local Ganache node
w3.eth.defaultAccount = w3.eth.accounts[0]

# Compile the smart contract
with open('energyTrade.sol', 'r') as file:
    contract_source_code = file.read()

compiled_sol = compile_standard({
    "language": "Solidity",
    "sources": {
        "energyTrade.sol": {
            "content": contract_source_code
        }
    },
    "settings": {
        "outputSelection": {
            "*": {
                "*": ["abi", "evm.bytecode"]
            }
        }
    }
})

bytecode = compiled_sol['contracts']['energyTrade.sol']['EnergyTrading']['evm']['bytecode']['object']
abi = compiled_sol['contracts']['energyTrade.sol']['EnergyTrading']['abi']

# Deploy the contract
EnergyTrading = w3.eth.contract(abi=abi, bytecode=bytecode)
tx_hash = EnergyTrading.constructor().transact()
tx_receipt = w3.eth.waitForTransactionReceipt(tx_hash)

print(f"Contract deployed at address: {tx_receipt.contractAddress}")

