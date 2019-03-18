# crypto-invoice-payment
Crypto Invoice Payment System

Requirements

- Java 8
- Maven 3

To run it:
> mvn spring-boot run

Instructions

- Access: http://localhost:8080/index.html and create an invoice
- Pay it using any wallet
- See the invoice changing status automatically


If you run it in `app.testmode = true` and then you want to run as `app.testmode = false`
you shoud delete the `wallet.file and blockstore.file` due to incompatibility in testnet and mainnet generated wallets. 


### Please, read this first: 

- ##### Important, this system considers PENDING transactions as a payment received. Future improvements can achieve that. 
- ##### This is only sample code to show an invoice system features, use it at your own risk. 
