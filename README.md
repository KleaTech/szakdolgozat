# Thesis

### This was my bachelor thesis in 2019. There are a lot of stuff I'd have done differently if I had the chance to start over. Especially around the frontend area, it's pretty chaotic. But I still agree with the basic concept that you can read in the abstract. The thesis is only available in Hungarian in the below link. If you are interested this is where you should go next.

[Thesis](https://github.com/KleaTech/szakdolgozat/blob/master/dolgozat.pdf)

## Abstract

Firstly, this thesis demonstrates the module system of Java 9. I shall present it's function and usability from a practical standpoint with examples. Despite the importance of this system, it did not quite caught up after 1.5 years. The reader can get to know the possible reasons to this and that what it means for a Java developer to use the module system.

Secondly, I shall present ways to connect external logic into a Java application. According to the concept of this thesis, 'logic' is similar to 'data' in a sense that it can be dynamically changed. The reader can get to know how it is possible to suite an application to similar tasks without altering the inner program code. I shall specify the possible technical solutions covering the subject of security, too. Currently there is no ready-to-use solution to connect external logic securely to a Java application without using an external utility. I also managed to achieve partial results only.

I made an application for demonstration purposes, one that utilizes the Java module system and is using external logic. The application was made with functional point of view, so it uses the external functions themselves instead of calculated values. These function can be changed at runtime and they can be written in 3 different script languages, but support for any language can be added easily. Together with the logic part of the UI also comes from external source, so the interface can also change dynamically.

Implementing a true Java sandbox may be the subject of further research. I foreshadowed some ways to achieve it, but it cannot be covered in this document. It may be worth find alternative ways to work around the circular dependency problem – the method I used is too cumbersome. Another improvement would be to replace the limited template-based UI with a REST-like approach. Furthermore the application is currently single-user – using the methods described in this thesis in a business environment would only be possible if this limitation is resolved.

I hope that this document provides guidance to those who just started to use the module system, but also shows some new facts to the more experienced. I hope that my work can be a starting point to realize the true Java sandbox. I also hope that the reader is enriched by a new perspective, one that collates data and logic. The presented methods may be useful for any application that uses plugins, snippets or other kinds of external logic.
