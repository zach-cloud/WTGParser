package com.github.zachcloud.cli;

import com.github.zachcloud.interfaces.IWtgMapper;
import com.github.zachcloud.wtg.WtgMapper;
import com.github.zachcloud.wtg.structure.WtgRoot;

import java.util.Scanner;

public class CLI {

    private void run() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a filename: ");
        String filename = in.nextLine();
        IWtgMapper mapper = new WtgMapper();
        WtgRoot root = mapper.read(filename);
        System.out.println(root.toString());
    }

    public static void main(String[] args) {
        new CLI().run();
    }

}
