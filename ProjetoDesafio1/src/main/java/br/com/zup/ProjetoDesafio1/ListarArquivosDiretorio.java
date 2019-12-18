package br.com.zup.ProjetoDesafio1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ListarArquivosDiretorio {

    public static void ListarArquivosDiretorio(File file, int nvl) throws IOException {
        String esp = "";
        int nivelDir = 0 + nvl;
        int i = 0;

        File files[] = file.listFiles();

        while(i < nivelDir){
            esp = esp + "-----";
            i++;
        }

        for(File f : files){
            if(f.isDirectory()){
                System.out.println(esp + " Diretorio: "+ f.getCanonicalPath());
                ListarArquivosDiretorio(f, ++nivelDir);
                nivelDir = 0;
            }else{
                System.out.println(esp + " Arquivo: "+ f.getName());
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Digite o caminho do diretorio que deseja listar os arquivos: ");
        String diretorio = s.nextLine();
        System.out.println("\n");
        try {
            File currentDir = new File(diretorio.replace("\\","\\"));
            ListarArquivosDiretorio(currentDir, 0);
        } catch (NullPointerException | IOException e) {
            System.out.println("Desculpe, diretorio nao encontrado!");
        }
    }
}