```c
semaphore plate = 1, apple = 0, orange = 0, friut_num = n;

void dad() {
    while(true) {
        choice = getChoice();
        P(friut_num);
        P(plate);
        if(choice == Apple) {
            V(apple);
        } else {
            V(orange);
        }
        V(plate);
    }
}

void son() {
    while(true) {
        P(apple);
        P(plate);
        getApple();
        V(friut_num);
        V(plate);
    }
}

void daughter() {
    while(true) {
        P(orange);
        P(plate);
        getOrange();
        V(friut_num);
        V(plate);
    }
}

```



```c
semaphore empty = 1, plate = 1, apple = 0, orange = 0;
void dad() {
    while(true) {
        P(empty);
        P(plate);
        V(apple);
        V(plate);
    }
}

void mon() {
    while(true) {
        P(empty);
        P(plate);
        V(orange);
        V(plate);
    }
}

void son() {
    while(true) {
       	P(apple);
        P(plate);
       	V(empty);
        V(plate);
    }
}

void daughter() {
    while(true) {
       	P(orange);
        P(plate);
       	V(empty);
        V(plate);
    }
}
```



$$\times$$

```c
semaphore P1WA = 1, P2RA = 0, P3RA = 0, P4RB = 0, P4RC = 0;
void P1() {
    while(true) {
        P(P1WA);
        V(P2RA);
        V(P3RA);
    }
}

void P2() {
    while(true) {
        P(P2RA);
        V(P4RB);
    }
}

void P3() {
    while(true) {
        P(P3RA);
        V(P4RB);
    }
}

void P4() {
    while(true) {
        P(P4RB);
        P(P4RC);
    }
}
```



读者优先

```c
semaphore mutex = 1, write = 1;
int reader_num = 0;
void writer() {
    while(true) {
        P(write);
        V(write);
    }
}

void reader() {
    while(true) {
        P(mutex);
        reader_num++;
        if(reader_num == 1) {
            P(write);
        }
        V(mutex);
        
        P(mutex);
        reader_num--;
        if(reader_num == 0) {
            V(write);
        }
        V(mutex);
    }
}
```



写者优先

```c
semaphore mutex = 1, read = 1;
int writer_num = 0;
void writer() {
    while(true) {
       	P(mutex);
        writer_num++;
        if(writer_num == 1) {
            P(read);
        }
        V(mutex);
        
        P(mutex);
        writer_num--;
        if(writer_num == 0) {
            V(read);
        }
        V(mutex);
    }
}

void reader() {
    while(true) {
        P(read);
        V(read);
    }
}
```

