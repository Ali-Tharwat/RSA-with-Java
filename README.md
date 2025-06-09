# RSA Encryption & Decryption 🔐
*Information Security Assignment 2 - German International University (Spring 2025)* : **Java implementation of the RSA algorithm**  

## 🔧 Implementation

### 🔑 Key Features
- **Secure key generation** using `BigInteger.probablePrime()`
- **256+ bit modulus** 
- **File I/O operations** for practical encryption/decryption

### 🛠️ Tech Stack

<p align="left">
  <img src="https://img.shields.io/badge/Java-OpenJDK-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Cryptography-%235C33E6.svg?style=for-the-badge&logo=internet-explorer&logoColor=white" alt="Cryptography">
</p>

**Core Libraries**:
- `java.math.BigInteger` - For large integer operations
- `java.security.SecureRandom` - Cryptographically secure RNG
- `java.nio.file` - File handling

## 💻 How to Use :

1. **Prepare input file** (`message.txt`) in project directory
2. **Compile and run**:
   ```bash
   javac RSA.java
   java RSA
