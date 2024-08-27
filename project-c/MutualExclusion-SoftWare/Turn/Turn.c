#include<pthread.h>
#include<stdio.h>
#include<stdlib.h>

void * func(void * arg)
{
    while(1)
    {
        printf("func run...\n");
    }
    return NULL;
}
int main()
{

        pthread_t t1;
        int err = pthread_create(&t1,NULL,func,NULL);

        return 0;
}