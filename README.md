# Simple ATM system
Java application who simulate software on ATM.
Application is able to create and delete accounts.
It is also able to add income and transfer money to other accounts.

When creating accounts, Luhn algorithm is used to validate a credit card number.

# Technology
- Java 11
- SQLite

# To run application:
There are no any build automation tool used. We have to compile and run manually. :)

To compile files, navigate to the project root directory and run following command:
> javac -cp ./libs/sqlite-jdbc-3.39.2.1.jar -d ./out/simple-atm-system -sourcepath . src/atm/*.java src/atm/*/*.java

To run application, navigate to the project root directory and run following command:
> java -cp ./libs/sqlite-jdbc-3.39.2.1.jar:./out/simple-atm-system atm.Main

# Examples
The symbol **>** represents the user input.

Create and log into a new account:
```
1. Create account
2. Log into account
0. Exit
> 1

Your card has been created
Your card number:
4000007794531423
Your card PIN:
5423

1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
XXX

Wrong card number or PIN!

1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
5423

You have successfully logged in!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 0

Bye!
```

Check balance and add income to existing account:
```
1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
5423

You have successfully logged in!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 1

Balance: 0

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 2

Enter income:
100
Income was added!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 1

Balance: 100

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 0

Bye!
```
Transfer money to another account:
```
1. Create account
2. Log into account
0. Exit
> 1

Your card has been created
Your card number:
4000007439330173
Your card PIN:
4419

1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
5423

You have successfully logged in!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
4000007794531423
You can't transfer money to the same account!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
4000007439330174
Probably you made a mistake in the card number. Please try again!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
4000007439330173
Enter how much money you want to transfer:
200
Not enough money!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
4000007439330173
Enter how much money you want to transfer:
100
Success!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 1

Balance: 0

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 0

Bye!
```
Delete existing account:
```
1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
5423

You have successfully logged in!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 4
The account has been closed!


You have successfully logged out!

1. Create account
2. Log into account
0. Exit
> 2

Enter your card number:
4000007794531423
Enter your PIN:
5423

Wrong card number or PIN!

1. Create account
2. Log into account
0. Exit
> 0

Bye!
```
# Licence
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)