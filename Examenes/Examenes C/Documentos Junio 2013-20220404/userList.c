#include "userList.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

T_user *createUser(char *name, int uid, char *dir) {
  T_user *newUser = malloc(sizeof(T_user));

  if (newUser == NULL) {
    perror("Error. No hay memoria suficiente");
    exit(-1);
  } else {
    newUser->userName_ = name;
    // strcpy(newUser->userName_, name);
    newUser->uid_ = uid;
    newUser->homeDirectory_ = dir;
    // strcpy(newUser->homeDirectory_, dir);
    newUser->nextUser_ = NULL;
    newUser->previousUser_ = NULL;
    return newUser;
  }
}

T_userList createUserList() {
  T_userList *newList = malloc(sizeof(T_userList));
  if (newList == NULL) {
    perror("Error. No hay memoria suficiente");
    exit(-1);
  } else {
    newList->head_ = NULL;
    newList->tail_ = NULL;
    newList->numberOfUsers_ = 0;
    return *newList;
  }
}

int addUser(T_userList *list, T_user *user) {
  int ok = 1;

  // while (aux->previousUser_ != NULL &&
  //      (aux->userName_ != user->userName_ && aux->uid_ != user->uid_)) {
  // aux = aux->previousUser_;
  //}

  if (list->numberOfUsers_ == 0) {
    list->head_ = user;
    list->tail_ = user;
    list->numberOfUsers_++;
  } else {
    if (list->numberOfUsers_ == 1 &&
        list->head_->userName_ != user->userName_ &&
        list->head_->uid_ != user->uid_) {
      list->tail_->nextUser_ = user;
      user->previousUser_ = list->tail_;
      list->head_ = user;
      list->numberOfUsers_++;
    } else {
      T_user *aux = list->head_;

      while (aux->previousUser_ != NULL &&
             (aux->userName_ != user->userName_ && aux->uid_ != user->uid_)) {
        aux = aux->previousUser_;
      }
      if (aux->userName_ == user->userName_ || aux->uid_ == user->uid_) {
        ok = 0;
      } else {
        list->head_->nextUser_ = user;
        user->previousUser_ = list->head_;
        list->head_ = user;
        list->numberOfUsers_++;
      }
    }
  }

  return ok;
}

T_user *buscarUser(T_userList lUser, char *userName_) {
  T_user *aux = lUser.head_;
  while (aux != NULL && aux->userName_ != userName_) {
    aux = aux->previousUser_;
  }
  return aux;
}

int getUid(T_userList list, char *userName) {
  T_user *x = buscarUser(list, userName);
  return x->uid_;
}

int deleteUser(T_userList *list, char *userName) {
  int ok = 0;
  T_user *user2delete = buscarUser(*list, userName);

  if (strcmp(user2delete->userName_, (*list).head_->userName_) == 0) {
    (*list).head_ = (*list).head_->previousUser_;
    free(user2delete);
    user2delete = NULL;
    (*list).head_->nextUser_ = NULL;
    (*list).numberOfUsers_--;
    ok = 1;
  } else if (strcmp(user2delete->userName_, (*list).tail_->userName_) == 0) {
    (*list).tail_ = (*list).tail_->nextUser_;
    free(user2delete);
    user2delete = NULL;
    (*list).tail_->previousUser_ = NULL;
    (*list).numberOfUsers_--;
    ok = 1;
  } else {
    T_user *next = user2delete->nextUser_;
    T_user *prev = user2delete->previousUser_;
    next->previousUser_ = user2delete->previousUser_;
    prev->nextUser_ = user2delete->nextUser_;
    free(user2delete);
    user2delete = NULL;
    (*list).numberOfUsers_--;
    ok = 1;
  }
  return ok;
}

void printUserList(T_userList list, int reverse) {
  if (list.numberOfUsers_ == 0) {
    printf("Lista vacía \n");
  } else {
    T_user *it;
    if (reverse == 0) {
      it = list.head_;
      while (it != NULL) {
        printf("Nombre: %s, Uid: %i, Dirección: %s \n", it->userName_, it->uid_,
               it->homeDirectory_);
        it = it->previousUser_;
      }
    } else {
      it = list.tail_;
      while (it != NULL) {
        printf("Nombre: %s, Uid: %i, Dirección: %s \n", it->userName_, it->uid_,
               it->homeDirectory_);
        it = it->nextUser_;
      }
    }
  }
}
