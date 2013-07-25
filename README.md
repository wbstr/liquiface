Liquiface - A simpified interface for LiquiBase
=========

The idea

A few years ago we had a FedEx day project in mind when we had an idea: “We've been using Liquibase at our company
for a while but until now changelog files were edited manually. It would be good to support the maintenance of
changelog files by a graphical interface. And if we're already using NetBeans, it is convenient to have this graphical
interface accessed from within NetBeans.”

What is Liquibase?

Liquibase is a database version tracking tool. In principle: "We never develop a code without version control. Then
why not to do this in database development?” Liquibase was developed in Java with an open-source code. Database
modifications are registered in xml (changelog) files. The database itself stores which changes have already been run
on it. Liquibase on the one hand is a program that can be run to apply the changes on a selected database, while on
the other hand it also provides a Java API to manage database modifications. Currently it does not have a graphic
interface, and that is exactly what we are working on right now.

Why is it called Liquiface?

If it is a GUI (Graphical User InterFace) to Liquibase, i.e. the "face of Liquibase", what else could we call it?

More info:
  - Homepage: http://liquiface.org/
  - What is Liquiface? http://blog.webstarworks.com/2013/07/what-is-liquiface
  - Liquiface user guide: http://blog.webstarworks.com/2013/07/liquiface-user-guide
